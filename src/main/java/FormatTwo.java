import org.json.simple.JSONObject;

public class FormatTwo {
    public String crawlId;
    public Integer total;
    public Integer totalInt;
    public Integer totalExt;

    public String getCrawlId() {
        return crawlId;
    }

    public void setCrawlId(String crawlId) {
        this.crawlId = crawlId;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalInt() {
        return totalInt;
    }

    public void setTotalInt(Integer totalInt) {
        this.totalInt = totalInt;
    }

    public Integer getTotalExt() {
        return totalExt;
    }

    public void setTotalExt(Integer totalExt) {
        this.totalExt = totalExt;
    }

    @Override
    public String toString() {
        return "{ \n" +
                "crawlId: "+crawlId+", \n"+
                "total: "+total+", \n"+
                "totalInt: "+totalInt+", \n"+
                "totalExt: "+totalExt+
                "} \n";
    }
}
