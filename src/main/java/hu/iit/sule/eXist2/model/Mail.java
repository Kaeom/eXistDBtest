package hu.iit.sule.eXist2.model;

public class Mail {
    private String senderName;
    private String senderMail;
    private String reciaverMail;
    private String ccMail;
    private String bccMail;
    private String mailSubject;
    private String mailText;

    public Mail() {
    }

    public Mail(String senderName, String senderMail, String reciaverMail, String ccMail, String bccMail, String mailSubject, String mailText) {
        this.senderName = senderName;
        this.senderMail = senderMail;
        this.reciaverMail = reciaverMail;
        this.ccMail = ccMail;
        this.bccMail = bccMail;
        this.mailSubject = mailSubject;
        this.mailText = mailText;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderMail() {
        return senderMail;
    }

    public void setSenderMail(String senderMail) {
        this.senderMail = senderMail;
    }

    public String getReciaverMail() {
        return reciaverMail;
    }

    public void setReciaverMail(String reciaverMail) {
        this.reciaverMail = reciaverMail;
    }

    public String getCcMail() {
        return ccMail;
    }

    public void setCcMail(String ccMail) {
        this.ccMail = ccMail;
    }

    public String getBccMail() {
        return bccMail;
    }

    public void setBccMail(String bccMail) {
        this.bccMail = bccMail;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public String getMailText() {
        return mailText;
    }

    public void setMailText(String mailText) {
        this.mailText = mailText;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "senderName='" + senderName + '\'' +
                ", senderMail='" + senderMail + '\'' +
                ", reciaverMail='" + reciaverMail + '\'' +
                ", ccMail='" + ccMail + '\'' +
                ", bccMail='" + bccMail + '\'' +
                ", mailSubject='" + mailSubject + '\'' +
                ", mailText='" + mailText + '\'' +
                '}';
    }
}
