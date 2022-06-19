package app.sender;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import java.io.File;

public class MailSender extends Sender {

    private final String smtp;
    private final String email_from;
    private final String email_password;
    private final String email_to;
    private final int smtpPort;
    private final boolean ssl;
    private final boolean tls;
    private final boolean debug;
    private final EmailAttachment attachmentFile;

    public MailSender(String smtp, String email_from, String email_password,
                      String email_to, int smtpPort, boolean ssl,
                      boolean tls, boolean debug) {
        this.smtp = smtp;
        this.email_from = email_from;
        this.email_password = email_password;
        this.email_to = email_to;
        this.smtpPort = smtpPort;
        this.ssl = ssl;
        this.tls = tls;
        this.debug = debug;
        attachmentFile = new EmailAttachment();
    }

    public static MailSenderBuilder builder() {
        return new MailSenderBuilder();
    }

    public void sendEmailAttachment(String file_path) throws EmailException {
        File fileScreenshot = new File(file_path);
        attachmentFile.setPath(fileScreenshot.getPath());
        attachmentFile.setDisposition(EmailAttachment.ATTACHMENT);
        attachmentFile.setDescription("Screenshot");
        attachmentFile.setName(fileScreenshot.getName());
        MultiPartEmail email = getEmail();
        email.setMsg("This email was send automatically");
        email.attach(attachmentFile);
        email.send();
    }

    @Override
    public boolean sendFile(File file) {
        try {
            sendEmailAttachment(file.getPath());
        } catch (EmailException e) {
            return false;
        }
        return true;
    }



    private MultiPartEmail getEmail() throws EmailException {
        MultiPartEmail email = new MultiPartEmail();
        email.setDebug(debug);
        email.setHostName(smtp);
        email.addTo(email_to);
        email.setSmtpPort(smtpPort);
        email.setFrom(email_from);
        email.setAuthentication(email_from, email_password);
        email.setSubject("Prodigy-inc");
        email.setSSL(ssl);
        email.setTLS(tls);
        return email;
    }

    public String getEmail_from() {
        return email_from;
    }

    public String getEmail_password() {
        return email_password;
    }

    public String getEmail_to() {
        return email_to;
    }

    @Override
    public boolean sendText(String text) {
        try {
            MultiPartEmail email = getEmail();
            email.setMsg(text);
            email.send();
        } catch (EmailException e) {
            return false;
        }
        return true;
    }

    public static class MailSenderBuilder {

        private String smtp;
        private String email_from;
        private String email_password;
        private String email_to;
        private int smtpPort;
        private boolean ssl;
        private boolean tls;
        private boolean debug;

        public MailSender build() {
            return new MailSender(smtp, email_from, email_password, email_to, smtpPort, ssl, tls, debug);
        }

        public MailSenderBuilder smtp(String smtp) {
            this.smtp = smtp;
            return this;
        }

        public MailSenderBuilder email_from(String email_from) {
            this.email_from = email_from;
            return this;
        }

        public MailSenderBuilder email_password(String email_password) {
            this.email_password = email_password;
            return this;
        }

        public MailSenderBuilder email_to(String email_to) {
            this.email_to = email_to;
            return this;
        }

        public MailSenderBuilder smtpPort(int smtpPort) {
            this.smtpPort = smtpPort;
            return this;
        }

        public MailSenderBuilder ssl(boolean ssl) {
            this.ssl = ssl;
            return this;
        }

        public MailSenderBuilder tls(boolean tls) {
            this.tls = tls;
            return this;
        }

        public MailSenderBuilder debug(boolean debug) {
            this.debug = debug;
            return this;
        }



        private MailSenderBuilder(){}

    }
}
