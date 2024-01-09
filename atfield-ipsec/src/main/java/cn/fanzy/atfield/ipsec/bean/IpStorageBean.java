package cn.fanzy.atfield.ipsec.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class IpStorageBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 6947296438342473477L;
    /**
     * 允许的ip列表
     */
    private Set<String> allowedIpList;
    /**
     * 拒绝的ip列表
     */
    private Set<String> deniedIpList;
}
