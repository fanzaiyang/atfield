package me.chanjar.weixin.cp.bean.oa.applydata;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * The type Content value.
 *
 * @author element
 */
@Data
@Accessors(chain = true)
public class ContentValue implements Serializable {
  private static final long serialVersionUID = -5607678965965065261L;

  private String text;

  @SerializedName("new_number")
  private String newNumber;

  @SerializedName("new_money")
  private String newMoney;

  private Date date;

  private Selector selector;

  private List<Member> members;

  private List<Department> departments;

  private List<File> files;

  private List<Child> children;

  @SerializedName("related_approval")
  private List<RelatedApproval> relatedApproval;

  private Attendance attendance;

  private Vacation vacation;

  @SerializedName("date_range")
  private Attendance.DataRange dateRange;

  @SerializedName("punch_correction")
  private PunchCorrection punchCorrection;

  /**
   * The type Date.
   */
  @Data
  public static class Date implements Serializable {
    private static final long serialVersionUID = -6181554080062231138L;
    private String type;

    @SerializedName("s_timestamp")
    private String timestamp;
  }

  /**
   * The type Selector.
   */
  @Data
  public static class Selector implements Serializable {
    private static final long serialVersionUID = 7305458759126951773L;
    private String type;
    private List<Option> options;

    /**
     * The type Option.
     */
    @Data
    public static class Option implements Serializable {
      private static final long serialVersionUID = -3471071106328280252L;
      private String key;

      @SerializedName("value")
      private List<ContentTitle> values;
    }

  }

  /**
   * The type Member.
   */
  @Data
  public static class Member implements Serializable {
    private static final long serialVersionUID = 1316551341955496067L;

    @SerializedName("userid")
    private String userId;
    private String name;
  }

  /**
   * The type Department.
   */
  @Data
  public static class Department implements Serializable {
    private static final long serialVersionUID = -2513762192924826234L;

    @SerializedName("openapi_id")
    private String openApiId;
    private String name;
  }

  /**
   * The type File.
   */
  @Data
  public static class File implements Serializable {
    private static final long serialVersionUID = 3890971381800855142L;

    @SerializedName("file_id")
    private String fileId;
  }

  /**
   * The type Child.
   */
  @Data
  public static class Child implements Serializable {
    private static final long serialVersionUID = -3500102073821161558L;
    private List<ApplyDataContent> list;
  }

  /**
   * The type Attendance.
   */
  @Data
  public static class Attendance implements Serializable {
    private static final long serialVersionUID = -6627566040706594166L;
    @SerializedName("date_range")
    private DataRange dateRange;
    private Integer type;

    /**
     * The type Data range.
     */
    @Data
    public static class DataRange implements Serializable {
      private static final long serialVersionUID = -3411836592583718255L;
      private String type;
      @SerializedName("new_begin")
      private Long begin;
      @SerializedName("new_end")
      private Long end;
      @SerializedName("new_duration")
      private Long duration;
    }
  }

  /**
   * The type Vacation.
   */
  @Data
  public static class Vacation implements Serializable {
    private static final long serialVersionUID = 2120523160034749170L;
    private Selector selector;
    private Attendance attendance;
  }

  /**
   * 关联审批单控件
   */
  @Data
  public static class RelatedApproval implements Serializable {
    private static final long serialVersionUID = 8629601623267510738L;
    /**
     * 关联审批单的模板名
     */
    @SerializedName("template_names")
    private List<TemplateName> templateNames;
    /**
     * 关联审批单的状态
     */
    @SerializedName("sp_status")
    private Integer spStatus;
    /**
     * 关联审批单的提单者
     */
    private String name;
    /**
     * 关联审批单的提单时间
     */
    @SerializedName("create_time")
    private Long createTime;
    /**
     * 关联审批单的审批单号
     */
    @SerializedName("sp_no")
    private String spNo;
  }

  /**
   * The type Template name.
   */
  @Data
  public static class TemplateName implements Serializable {
    private static final long serialVersionUID = 3152481506054355937L;
    private String text;
    private String lang;
  }

  /**
   * The type Punch correction.
   */
  @Data
  public static class PunchCorrection implements Serializable {
    private static final long serialVersionUID = 2120523160034749170L;
    private String state;
    private Long time;
  }

}
