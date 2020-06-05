package de.sharetrip.message.repository;

import de.sharetrip.mail.domain.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<Mail, Long> {

}
