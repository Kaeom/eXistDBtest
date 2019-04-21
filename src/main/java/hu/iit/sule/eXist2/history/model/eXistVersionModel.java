package hu.iit.sule.eXist2.history.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="v:revision")
public class eXistVersionModel {
    @XmlElement(name = "rev")
    private int rev;
    @XmlElement(name = "v:Date")
    private String date;
    @XmlElement(name = "v:Name")
    private String user;


    public eXistVersionModel() {
    }

    public eXistVersionModel(int rev, String date, String user) {
        this.rev = rev;
        this.date = date;
        this.user = user;
    }

    public int getRev() {
        return rev;
    }

    public void setRev(int rev) {
        this.rev = rev;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "eXistVersionModel{" +
                "rev=" + rev +
                ", date='" + date + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}
