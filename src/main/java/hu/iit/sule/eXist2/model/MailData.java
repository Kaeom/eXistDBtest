package hu.iit.sule.eXist2.model;

public class MailData {
    private String _to;
    private String _host;
    private int _port;
    private String _user;
    private String _pass;
    private String _subject;
    private String _text;

    public String getTo() {
        return _to;
    }

    public String getHost() {
        return _host;
    }

    public int getPort() {
        return _port;
    }

    public String getUser() {
        return _user;
    }

    public String getPass() {
        return _pass;
    }

    public String getSubject() {
        return _subject;
    }

    public String getText() {
        return _text;
    }

    public void setText(String text)
    {
        _text=text;
    }
    public void setSubject(String subject)
    {
        _subject=subject;
    }
    public MailData(String to, String host,int port, String user, String pass, String subject, String text)
    {
        _to=to;
        _host=host;
        _port=port;
        _user=user;
        _pass=pass;
        _subject=subject;
        _text=text;
    }

}