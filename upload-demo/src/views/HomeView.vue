<template>
  <main>
    <el-card style="width: 400px; margin: 80px auto" header="文件分片上传">
      <el-upload
          class="upload-demo"
          drag
          action="/"
          multiple
          :http-request="handleHttpRequest"
          :on-remove="handleRemoveFile">
        <el-icon class="el-icon--upload">
          <upload-filled/>
        </el-icon>
        <div class="el-upload__text">
          请拖拽文件到此处或 <em>点击此处上传</em>
        </div>
      </el-upload>
    </el-card>
  </main>
</template>
<script setup lang="ts">
import {UploadFilled} from '@element-plus/icons-vue'
import Queue from 'promise-queue-plus'
import md5 from "@/utils/md5";
import {initTask, merge} from '@/utils/api';
import {ElNotification} from "element-plus";
import axios from 'axios'
import {ref} from 'vue'

// 文件上传分块任务的队列（用于移除文件时，停止该文件的上传队列） key：fileUid value： queue object
const fileUploadChunkQueue = ref<any>({}).value

/**
 * 获取一个上传任务，没有则初始化一个
 */
const getTaskInfo = async (file: any) => {
  let task:any;
  const identifier = await md5(file)
  console.log(`文件MD5值：${identifier}`);
  const initTaskData = {
    identifier,
    fileName: file.name,
    fileSize: file.size,
    chunkSize: 5 * 1024 * 1024
  }
  const {code, data, message}: any = await initTask(initTaskData)
  if (code === 200) {
    task = data
  } else {
    ElNotification.error({
      title: '文件上传错误',
      message: message
    })
  }
  return task
}

interface TaskRecord {
  identifier: string;
  finished: boolean;
  chunkSize: number;
  totalChunks: number;
  partList: Array<any>;
}

/**
 * 上传逻辑处理，如果文件已经上传完成（完成分块合并操作），则不会进入到此方法中
 */
const handleUpload = (file: any, taskRecord: TaskRecord, options: any) => {
  let lastUploadedSize = 0; // 上次断点续传时上传的总大小
  let uploadedSize = 0 // 已上传的大小
  const totalSize = file.size || 0 // 文件总大小
  let startMs = new Date().getTime(); // 开始上传的时间
  const {partList, chunkSize, totalChunks, identifier} = taskRecord
  console.log('taskRecord',taskRecord);

  // 获取从开始上传到现在的平均速度（byte/s）
  const getSpeed = () => {
    // 已上传的总大小 - 上次上传的总大小（断点续传）= 本次上传的总大小（byte）
    const intervalSize = uploadedSize - lastUploadedSize
    const nowMs = new Date().getTime()
    // 时间间隔（s）
    const intervalTime = (nowMs - startMs) / 1000
    return intervalSize / intervalTime
  }

  const uploadNext = async (partNumber: number) => {
    console.log(`uploadNext->${partNumber}`);
    const start = chunkSize * (partNumber - 1)
    const end = start + chunkSize
    const blob = file.slice(start, end)
    console.log(start,end,blob);
    let filter = partList.filter(item=>item.currentPartNumber==partNumber);
    if(filter.length===0){
      return Promise.reject("未找到分片「"+partNumber+"」上传地址");
    }
    if(filter[0].finished){
      return Promise.resolve({partNumber: partNumber, uploadedSize: blob.size});
    }
    await axios.request({
      url: filter[0].uploadUrl,
      method: 'PUT',
      data: blob,
      headers: {'Content-Type': 'application/octet-stream'}
    })
    return Promise.resolve({partNumber: partNumber, uploadedSize: blob.size})
  }

  /**
   * 更新上传进度
   * @param increment 为已上传的进度增加的字节量
   */
  const updateProcess = (increment: number) => {
    const {onProgress} = options
    let factor = 1000; // 每次增加1000 byte
    let from = 0;
    // 通过循环一点一点的增加进度
    while (from <= increment) {
      from += factor
      uploadedSize += factor
      const percent = Math.round(uploadedSize / totalSize * 100).toFixed(2);
      onProgress({percent: percent})
    }

    const speed = getSpeed();
    const remainingTime = speed != 0 ? Math.ceil((totalSize - uploadedSize) / speed) + 's' : '未知'
    console.log('剩余大小：', (totalSize - uploadedSize) / 1024 / 1024, 'mb');
    console.log('当前速度：', (speed / 1024 / 1024).toFixed(2), 'mbps');
    console.log('预计完成：', remainingTime);
  }

  return new Promise(resolve => {
    const failArr: Array<any> = [];
    const queue = Queue(5, {
      "retry": 3,               //Number of retries
      "retryIsJump": false,     //retry now?
      "workReject": function (reason, queue) {
        failArr.push(reason)
      },
      "queueEnd": function (queue: any) {
        resolve(failArr);
      }
    })
    fileUploadChunkQueue[file.uid] = queue
    console.log(`总分片：${totalChunks}`);
    for (let partNumber = 1; partNumber <= totalChunks; partNumber++) {
      const exitPart = (partList || []).find((exitPart: { currentPartNumber: number; size: number;finished:boolean }) => exitPart.finished)
      console.log(`分片${partNumber}是否存在：${exitPart}`);
      if (exitPart) {
        // 分片已上传完成，累计到上传完成的总额中,同时记录一下上次断点上传的大小，用于计算上传速度
        lastUploadedSize += exitPart.size
        updateProcess(exitPart.size)
      } else {
        queue.push(() => uploadNext(partNumber).then(res => {
          // 单片文件上传完成再更新上传进度
          updateProcess(res.uploadedSize)
        }))
      }
    }
    if (queue.getLength() == 0) {
      // 所有分片都上传完，但未合并，直接return出去，进行合并操作
      resolve(failArr);
      return;
    }
    queue.start()
  })
}

/**
 * el-upload 自定义上传方法入口
 */
const handleHttpRequest = async (options: any) => {
  const file = options.file
  const task = await getTaskInfo(file)
  if (task) {
    const {finished, path, partList, identifier} = task
    if (finished) {
      return path
    } else {
      const errorList: any = await handleUpload(file, task, options)
      if (errorList.length > 0) {
        ElNotification.error({
          title: '文件上传错误',
          message: '部分分片上次失败，请尝试重新上传文件'
        })
        return;
      }
      const {code, data, message}: any = await merge(identifier)
      if (code === 200) {
        return path;
      } else {
        ElNotification.error({
          title: '文件上传错误',
          message: message
        })
      }
    }
  } else {
    ElNotification.error({
      title: '文件上传错误',
      message: '获取上传任务失败'
    })
  }
}

/**
 * 移除文件列表中的文件
 * 如果文件存在上传队列任务对象，则停止该队列的任务
 */
const handleRemoveFile = (uploadFile: any, uploadFiles: any) => {
  const queueObject = fileUploadChunkQueue[uploadFile.uid]
  if (queueObject) {
    queueObject.stop()
    fileUploadChunkQueue[uploadFile.uid] = undefined
  }
}
</script>
