package hu.iit.sule.eXist2.mailSenders;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SmtpAuthenticator extends Authenticator {
    private String _user;
    private String _pass;

    public SmtpAuthenticator(String user, String pass) {

        super();
        _user=user;
        _pass=pass;
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {

        if ((_user != null) && (!_user.isEmpty()) && (_pass != null)
                && (!_pass.isEmpty())) {

            return new PasswordAuthentication(_user, _pass);
        }

        return null;
    }
}