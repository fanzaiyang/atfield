package cn.fanzy.atfield.leaf.service;

import cn.fanzy.atfield.leaf.core.IDGenerator;
import cn.fanzy.atfield.leaf.core.common.Result;
import cn.fanzy.atfield.leaf.core.common.Status;
import cn.fanzy.atfield.leaf.core.segment.SegmentIDGeneratorImpl;
import cn.fanzy.atfield.leaf.core.segment.model.LeafAlloc;
import cn.fanzy.atfield.leaf.core.segment.model.SegmentBuffer;
import cn.fanzy.atfield.leaf.model.SegmentBufferView;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SegmentService {

    private final IDGenerator idGenerator;

    public SegmentService(IDGenerator idGenerator) {
        this.idGenerator = idGenerator;
        if (idGenerator.init()) {
            log.info("Segment Service Init Successfully");
        } else {
            throw new RuntimeException("Segment Service Init Fail");
        }
    }

    /**
     * 获取段主键
     *
     * @param key 钥匙
     * @return {@link String }
     */
    public String getSegmentId(String key) {
        return get(key, getId(key));
    }

    /**
     * 获取主键
     *
     * @param key 钥匙
     * @return {@link Result }
     */
    public Result getId(String key) {
        return idGenerator.get(key);
    }


    /**
     * 获取主键generator
     *
     * @return {@link SegmentIDGeneratorImpl }
     */
    public SegmentIDGeneratorImpl getIdGenerator() {
        return (SegmentIDGeneratorImpl) idGenerator;
    }

    /**
     * 获取缓存
     *
     * @return {@link Map }<{@link String }, {@link SegmentBufferView }>
     */
    public Map<String, SegmentBufferView> getCache() {
        Map<String, SegmentBufferView> data = new HashMap<>();
        SegmentIDGeneratorImpl segmentIDGen = getIdGenerator();
        if (segmentIDGen == null) {
            throw new IllegalArgumentException("You should config leaf.segment.enable=true first");
        }
        Map<String, SegmentBuffer> cache = segmentIDGen.getCache();
        for (Map.Entry<String, SegmentBuffer> entry : cache.entrySet()) {
            SegmentBufferView sv = new SegmentBufferView();
            SegmentBuffer buffer = entry.getValue();
            sv.setInitOk(buffer.isInitOk());
            sv.setKey(buffer.getKey());
            sv.setPos(buffer.getCurrentPos());
            sv.setNextReady(buffer.isNextReady());
            sv.setMax0(buffer.getSegments()[0].getMax());
            sv.setValue0(buffer.getSegments()[0].getValue().get());
            sv.setStep0(buffer.getSegments()[0].getStep());

            sv.setMax1(buffer.getSegments()[1].getMax());
            sv.setValue1(buffer.getSegments()[1].getValue().get());
            sv.setStep1(buffer.getSegments()[1].getStep());

            data.put(entry.getKey(), sv);

        }
        return data;
    }

    /**
     * 获取数据库
     *
     * @param model 型
     * @return {@link List }<{@link LeafAlloc }>
     */
    public List<LeafAlloc> getDb() {
        SegmentIDGeneratorImpl segmentIDGen = getIdGenerator();
        if (segmentIDGen == null) {
            throw new IllegalArgumentException("You should config leaf.segment.enable=true first");
        }
        List<LeafAlloc> items = segmentIDGen.getAllLeafAllocs();
        log.info("DB info {}", items);
        return items;
    }

    private String get(String key, Result id) {
        Result result;
        if (key == null || key.isEmpty()) {
            throw new RuntimeException("Key is none");
        }
        result = id;
        if (result.getStatus().equals(Status.EXCEPTION)) {
            throw new RuntimeException(result.toString());
        }
        return String.valueOf(result.getId());
    }

}
