

-- -----------------------------------------------------
-- Schema bpms_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema bpms_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bpms_db` DEFAULT CHARACTER SET utf8 ;
USE `bpms_db` ;

-- -----------------------------------------------------
-- Table `bpms_db`.`priority`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bpms_db`.`priority` (
                                                    `id` INT NOT NULL,
                                                    `name` VARCHAR(45) NOT NULL,
    `value` INT NOT NULL,
    PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `bpms_db`.`department`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bpms_db`.`department` (
                                                      `id` INT NOT NULL,
                                                      `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `bpms_db`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bpms_db`.`user` (
                                                `id` INT NOT NULL AUTO_INCREMENT,
                                                `firstname` VARCHAR(45) NOT NULL,
    `futhername` VARCHAR(45) NOT NULL,
    `birth_day` DATE NULL,
    `email` VARCHAR(45) NOT NULL,
    `proitiry_id` INT NOT NULL,
    `department_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_user_proitiry1_idx` (`proitiry_id` ASC) VISIBLE,
    INDEX `fk_user_department1_idx` (`department_id` ASC) VISIBLE,
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    CONSTRAINT `fk_user_proitiry1`
    FOREIGN KEY (`proitiry_id`)
    REFERENCES `bpms_db`.`priority` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_user_department1`
    FOREIGN KEY (`department_id`)
    REFERENCES `bpms_db`.`department` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `bpms_db`.`project`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bpms_db`.`project` (
                                                   `id` INT NOT NULL,
                                                   `name` VARCHAR(45) NOT NULL,
    `created_at` DATETIME NOT NULL,
    `deadline` DATETIME NOT NULL,
    PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `bpms_db`.`assignment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bpms_db`.`assignment` (
                                                      `id` INT NOT NULL,
                                                      `start_at` DATETIME NOT NULL,
                                                      `created_at` DATETIME NOT NULL,
                                                      `deadline` DATETIME NOT NULL,
                                                      `creator_name` VARCHAR(45) NOT NULL,
    `description` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `bpms_db`.`report`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bpms_db`.`report` (
                                                  `id` INT NOT NULL,
                                                  `created_at` DATETIME NOT NULL,
                                                  `description` VARCHAR(45) NOT NULL,
    `edit_at` DATETIME NOT NULL,
    PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `bpms_db`.`task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bpms_db`.`task` (
                                                `id` INT NOT NULL,
                                                `report_id` INT NOT NULL,
                                                `assignment_id` INT NOT NULL,
                                                `priority_id` INT NOT NULL,
                                                PRIMARY KEY (`id`),
    INDEX `fk_task_report1_idx` (`report_id` ASC) VISIBLE,
    INDEX `fk_task_assignment1_idx` (`assignment_id` ASC) VISIBLE,
    INDEX `fk_task_priority1_idx` (`priority_id` ASC) VISIBLE,
    CONSTRAINT `fk_task_report1`
    FOREIGN KEY (`report_id`)
    REFERENCES `bpms_db`.`report` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_task_assignment1`
    FOREIGN KEY (`assignment_id`)
    REFERENCES `bpms_db`.`assignment` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_task_priority1`
    FOREIGN KEY (`priority_id`)
    REFERENCES `bpms_db`.`priority` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `bpms_db`.`form`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bpms_db`.`form` (
                                                `id` INT NOT NULL,
                                                `title` VARCHAR(45) NOT NULL,
    `body` VARCHAR(45) NOT NULL,
    `report_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_form_report1_idx` (`report_id` ASC) VISIBLE,
    CONSTRAINT `fk_form_report1`
    FOREIGN KEY (`report_id`)
    REFERENCES `bpms_db`.`report` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `bpms_db`.`limitation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bpms_db`.`limitation` (
                                                      `id` INT NOT NULL,
                                                      `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `bpms_db`.`doc_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bpms_db`.`doc_type` (
                                                    `id` INT NOT NULL,
                                                    `name` VARCHAR(45) NULL,
    PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `bpms_db`.`document`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bpms_db`.`document` (
                                                    `id` INT NOT NULL,
                                                    `load_at` DATETIME NOT NULL,
                                                    `path` VARCHAR(45) NOT NULL,
    `size` DOUBLE NOT NULL,
    `doc_type_id` INT NOT NULL,
    `department_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_document_doc_type1_idx` (`doc_type_id` ASC) VISIBLE,
    INDEX `fk_document_department1_idx` (`department_id` ASC) VISIBLE,
    CONSTRAINT `fk_document_doc_type1`
    FOREIGN KEY (`doc_type_id`)
    REFERENCES `bpms_db`.`doc_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_document_department1`
    FOREIGN KEY (`department_id`)
    REFERENCES `bpms_db`.`department` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `bpms_db`.`permits`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bpms_db`.`permits` (
                                                   `id` INT NOT NULL,
                                                   `limitation_id` INT NOT NULL,
                                                   `document_id` INT NOT NULL,
                                                   `user_id` INT NOT NULL,
                                                   PRIMARY KEY (`id`),
    INDEX `fk_permits_limitation1_idx` (`limitation_id` ASC) VISIBLE,
    INDEX `fk_permits_document1_idx` (`document_id` ASC) VISIBLE,
    INDEX `fk_permits_user1_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `fk_permits_limitation1`
    FOREIGN KEY (`limitation_id`)
    REFERENCES `bpms_db`.`limitation` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_permits_document1`
    FOREIGN KEY (`document_id`)
    REFERENCES `bpms_db`.`document` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_permits_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `bpms_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `bpms_db`.`notification`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bpms_db`.`notification` (
                                                        `id` INT NOT NULL,
                                                        `created_at` DATETIME NOT NULL,
                                                        `finish_at` DATETIME NOT NULL,
                                                        `user_id` INT NOT NULL,
                                                        PRIMARY KEY (`id`),
    INDEX `fk_notification_user1_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `fk_notification_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `bpms_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `bpms_db`.`project_has_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bpms_db`.`project_has_user` (
                                                            `project_id` INT NOT NULL,
                                                            `user_id` INT NOT NULL,
                                                            PRIMARY KEY (`project_id`, `user_id`),
    INDEX `fk_project_has_user_user1_idx` (`user_id` ASC) VISIBLE,
    INDEX `fk_project_has_user_project1_idx` (`project_id` ASC) VISIBLE,
    CONSTRAINT `fk_project_has_user_project1`
    FOREIGN KEY (`project_id`)
    REFERENCES `bpms_db`.`project` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_project_has_user_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `bpms_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `bpms_db`.`department_has_project`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bpms_db`.`department_has_project` (
                                                                  `department_id` INT NOT NULL,
                                                                  `project_id` INT NOT NULL,
                                                                  PRIMARY KEY (`department_id`, `project_id`),
    INDEX `fk_department_has_project_project1_idx` (`project_id` ASC) VISIBLE,
    INDEX `fk_department_has_project_department1_idx` (`department_id` ASC) VISIBLE,
    CONSTRAINT `fk_department_has_project_department1`
    FOREIGN KEY (`department_id`)
    REFERENCES `bpms_db`.`department` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_department_has_project_project1`
    FOREIGN KEY (`project_id`)
    REFERENCES `bpms_db`.`project` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `bpms_db`.`assignment_has_document`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bpms_db`.`assignment_has_document` (
                                                                   `document_id` INT NOT NULL,
                                                                   `assignment_id` INT NOT NULL,
                                                                   PRIMARY KEY (`document_id`, `assignment_id`),
    INDEX `fk_document_has_assignment_assignment1_idx` (`assignment_id` ASC) VISIBLE,
    INDEX `fk_document_has_assignment_document1_idx` (`document_id` ASC) VISIBLE,
    CONSTRAINT `fk_document_has_assignment_document1`
    FOREIGN KEY (`document_id`)
    REFERENCES `bpms_db`.`document` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_document_has_assignment_assignment1`
    FOREIGN KEY (`assignment_id`)
    REFERENCES `bpms_db`.`assignment` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `bpms_db`.`association`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bpms_db`.`association` (
                                                       `id` INT NOT NULL,
                                                       `creator` INT NOT NULL,
                                                       `assignment_id` INT NOT NULL,
                                                       `worker` INT NOT NULL,
                                                       PRIMARY KEY (`id`),
    INDEX `fk_association_user1_idx` (`creator` ASC) VISIBLE,
    INDEX `fk_association_assignment1_idx` (`assignment_id` ASC) VISIBLE,
    INDEX `fk_association_user2_idx` (`worker` ASC) VISIBLE,
    CONSTRAINT `fk_association_user1`
    FOREIGN KEY (`creator`)
    REFERENCES `bpms_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_association_assignment1`
    FOREIGN KEY (`assignment_id`)
    REFERENCES `bpms_db`.`assignment` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_association_user2`
    FOREIGN KEY (`worker`)
    REFERENCES `bpms_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;


-- -----------------------------------------------------
-- Table `bpms_db`.`role_in_company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bpms_db`.`role_in_company` (
                                                           `id` INT NOT NULL AUTO_INCREMENT,
                                                           `role_in_companycol` VARCHAR(12) NOT NULL,
    PRIMARY KEY (`id`))
;


-- -----------------------------------------------------
-- Table `bpms_db`.`user_role_in_company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bpms_db`.`user_role_in_company` (
                                                                `id` INT NOT NULL AUTO_INCREMENT,
                                                                `user_id` INT NOT NULL,
                                                                `company_id` INT NOT NULL,
                                                                `id_role_in_company` INT NOT NULL,
                                                                PRIMARY KEY (`id`),
    INDEX `user_id_fk_idx` (`user_id` ASC) VISIBLE,
    INDEX `company_id_fk_idx` (`company_id` ASC) VISIBLE,
    INDEX `id_role_in_company_idx` (`id_role_in_company` ASC) VISIBLE,
    CONSTRAINT `user_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `bpms_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `company_id_fk`
    FOREIGN KEY (`company_id`)
    REFERENCES `bpms_db`.`department` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT `id_role_in_company`
    FOREIGN KEY (`id_role_in_company`)
    REFERENCES `bpms_db`.`role_in_company` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
;



