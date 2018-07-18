package uni.miskolc.ips.ilona.tracking.controller.passwordrecovery;

import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import uni.miskolc.ips.ilona.tracking.controller.exception.PasswordRecoveryTokenSenderException;

public class EmailBasedTokenSenderImpl implements PasswordTokenSender {

	private static Logger logger = LogManager.getLogger(EmailBasedTokenSenderImpl.class);

	private JavaMailSender mailSender;

	public EmailBasedTokenSenderImpl(JavaMailSender mailSender) {
		super();
		this.mailSender = mailSender;

	}

	@Override
	public void sendToken(String userid, String token, String address) throws PasswordRecoveryTokenSenderException {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setTo(address);
			helper.setSubject("ILONA password recovery!");
			helper.setText("The new token is: <br><br> <b>userid:</b> " + userid + "<br> <b>token: </b>" + token, true);
			mailSender.send(message);

		} catch (Exception e) {
			logger.error("Email sending failed! userid: " + userid + " token: " + token + "Error: " + e.getMessage());
			throw new PasswordRecoveryTokenSenderException("Email send error!", e);
		}

	}

	@Override
	public void sendNewPassword(String userid, String newPassword, String address)
			throws PasswordRecoveryTokenSenderException {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setTo(address);
			helper.setSubject("ILONA password recovery!");
			helper.setText(
					"The new password is: <br><br> <b>userid:</b> " + userid + "<br> <b>password: </b>" + newPassword,
					true);
			mailSender.send(message);

		} catch (Exception e) {
			logger.error(
					"Email sending failed! userid: " + userid + " address: " + address + "Error: " + e.getMessage());
			throw new PasswordRecoveryTokenSenderException("Email send error!", e);
		}
	}
}
