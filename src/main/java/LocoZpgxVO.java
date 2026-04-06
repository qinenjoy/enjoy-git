import java.io.Serializable;

/**
 * 机车支配关系数据对象。
 * 说明：按老系统常见 VO 写法，字段使用字符串，便于后续迁移。
 */
public class LocoZpgxVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 机型 */
    private String model;
    /** 机车号码 */
    private String loco;
    /** 支配关系 */
    private String ZPGX;
    /** 开始时间 */
    private String changetime;
    /** 结束时间 */
    private String endtime;
    /** 创建的机务段 */
    private String createpostid;

    public LocoZpgxVO() {
    }

    public LocoZpgxVO(String model, String loco, String ZPGX, String changetime, String endtime, String createpostid) {
        this.model = model;
        this.loco = loco;
        this.ZPGX = ZPGX;
        this.changetime = changetime;
        this.endtime = endtime;
        this.createpostid = createpostid;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLoco() {
        return loco;
    }

    public void setLoco(String loco) {
        this.loco = loco;
    }

    public String getZPGX() {
        return ZPGX;
    }

    public void setZPGX(String ZPGX) {
        this.ZPGX = ZPGX;
    }

    public String getChangetime() {
        return changetime;
    }

    public void setChangetime(String changetime) {
        this.changetime = changetime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getCreatepostid() {
        return createpostid;
    }

    public void setCreatepostid(String createpostid) {
        this.createpostid = createpostid;
    }
}
