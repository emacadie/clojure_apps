CREATE TRIGGER trig_user_email_check BEFORE INSERT ON users
FOR EACH ROW 
BEGIN 
IF (NEW.email REGEXP '^.+@.+\..+$' ) = 0 THEN 
 SIGNAL SQLSTATE '45000'
     SET MESSAGE_TEXT = 'provided email address does not match regex';
END IF; 
END;

