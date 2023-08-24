package me.chanjar.weixin.cp.api.impl;

import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.util.json.GsonHelper;
import me.chanjar.weixin.common.util.json.GsonParser;
import me.chanjar.weixin.cp.api.WxCpOaMeetingRoomService;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.oa.meetingroom.WxCpOaMeetingRoom;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.util.List;

import static me.chanjar.weixin.cp.constant.WxCpApiPathConsts.Oa.*;

/**
 * The type Wx cp oa meeting room service.
 *
 * @author fcat
 * @version 1.0  Create by 2022/8/12 23:49
 */
@RequiredArgsConstructor
public class WxCpOaMeetingRoomServiceImpl implements WxCpOaMeetingRoomService {
  private final WxCpService wxCpService;

  @Override
  public String addMeetingRoom(WxCpOaMeetingRoom meetingRoom) throws WxErrorException {
    return this.wxCpService.post(this.wxCpService.getWxCpConfigStorage().getApiUrl(MEETINGROOM_ADD), meetingRoom);
  }

  @Override
  public List<WxCpOaMeetingRoom> listMeetingRoom(WxCpOaMeetingRoom meetingRoomRequest) throws WxErrorException {
    String response = this.wxCpService.post(this.wxCpService.getWxCpConfigStorage().getApiUrl(MEETINGROOM_LIST),
      meetingRoomRequest);
    return WxCpGsonBuilder.create().fromJson(GsonParser.parse(response).get("meetingroom_list").getAsJsonArray().toString(),
      new TypeToken<List<WxCpOaMeetingRoom>>() {
      }.getType());
  }

  @Override
  public void editMeetingRoom(WxCpOaMeetingRoom meetingRoom) throws WxErrorException {
    this.wxCpService.post(this.wxCpService.getWxCpConfigStorage().getApiUrl(MEETINGROOM_EDIT), meetingRoom);
  }

  @Override
  public void deleteMeetingRoom(Integer meetingRoomId) throws WxErrorException {
    this.wxCpService.post(this.wxCpService.getWxCpConfigStorage().getApiUrl(MEETINGROOM_DEL),
      GsonHelper.buildJsonObject("meetingroom_id", meetingRoomId));
  }
}
