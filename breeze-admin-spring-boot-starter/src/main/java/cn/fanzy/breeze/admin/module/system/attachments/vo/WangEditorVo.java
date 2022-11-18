package cn.fanzy.breeze.admin.module.system.attachments.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WangEditorVo {
    private Integer errno;
    private String message;
    private Body data;

    @Data
    public static class Body{
        private String url;
        private String alt;

        private String href;
    }
}
