USE `eng_hc`;

/*Data for the table `service_type` */

insert  into `service_type`(`id`,`identifier`,`name`) values 
(1,'SER-T1','Aged Care Assessment'),
(2,'SER-T2','Aged Residential Care'),
(3,'SER-T3','Home Care/Housekeeping assistance'),
(4,'SER-T4','Acupuncture'),
(5,'SER-T5','Bowen Therapy'),
(6,'SER-T6','Blood Donation'),
(7,'SER-T7','Family Planning'),
(8,'SER-T8','Immunization'),
(9,'SER-T9','Optometry'),
(10,'SER-T10','Osteopathy'),
(11,'SER-T11','Physiotherapy'),
(12,'SER-T12','Podiatry'),
(13,'SER-T13',' Endodontic'),
(14,'SER-T14','Dental'),
(15,'SER-T15','Oral Surgery'),
(16,'SER-T16','Emergency Medical'),
(17,'SER-T17','Psychology'),
(18,'SER-T18','Dermatology');

/*Data for the table `organization_type` */

insert  into `organization_type`(`id`,`identifier`,`name`) values 
(1,'ORG-T1','Hospital'),
(2,'ORG-T2','Insurance Company'),
(3,'ORG-T3','Educational Institute'),
(4,'ORG-T4','Clinical Research'),
(5,'ORG-T5','Other');

/*Data for the table `organization` */

insert  into `organization`(`id`,`active`,`adress`,`email`,`identifier`,`name`,`phone`,`type_id`) values 
(1,'','Main street 12A','ggeneral@gmail.com','ORG-#1','Gotham General','1234567',1),
(2,'','Central blvd 11','stbhosp@gmail.com','ORG-#2','St. Benedict\'s','12345891',1),
(3,'','Fury road 17B','jbros@gmail.com','ORG-#3','Johnson Brothers','22345818',2),
(4,'','Healer blvd 3','medic@gmail.com','ORG-#4','Medic\'s tale','22345719',2),
(5,'','Main street 27','mmacademy@gmail.com','ORG-#5','Military Medical Academy','23347818',3),
(6,'','Boston road 18C','cinstitute@yahoo.com','ORG-#6','Commonwealth Institute','22346719',3),
(7,'','Central blvd 89','cillres@yahoo.com','ORG-#7','Clinicalill','12345998',4),
(8,'','Central road 1','healingtemple@yahoo.com','ORG-#8','Healing temple','',5);


/*Data for the table `practitioner` */

insert  into `practitioner`(`id`,`active`,`adress`,`birth_date`,`email`,`gender`,`identifier`,`name`,`password`,`phone`,`qualification`,`surname`,`username`,`organization_id`) values 
(1,'','Main street 38','1985-11-11',NULL,'Male','PRACT1','Mark','1234',NULL,'Doctor of Medicine','Jameson','mark',1),
(2,'',NULL,'1995-03-22',NULL,'Male','PRACT2','Johnah',NULL,NULL,'Medical Assistant','Hill',NULL,5),
(5,'',NULL,'1987-02-02','jjohnson@gmail.com','Male','PRACT3','Jordan',NULL,NULL,'Doctor of Medicine','Johnson',NULL,3),
(6,'',NULL,'2000-08-19',NULL,'Female','PRACT4','Samantha',NULL,NULL,'Nurse Practitioner','Samuels',NULL,4),
(7,'',NULL,'1988-09-09',NULL,'Female','PRACT5','Maria',NULL,NULL,'Doctor of Medicine','Roberts',NULL,7),
(8,'','Gotham Palisades 1','1990-12-10','marthawayne@gmail.com','Female','PRACT6','Martha','bruce123',NULL,'Doctor of Pharmacy','Wayne','martha',1),
(9,'','Freedom trail 12','1958-09-07','wwhite@gmail.com','Male','PRACT7','Walter','heisenberg',NULL,'Doctor of Pharmacy','White','saymyname',6),
(10,'',NULL,'1984-09-23',NULL,'Male','PRACT8','Jesse',NULL,NULL,'Medical Assistant','Pinkman',NULL,6),
(11,'',NULL,'1958-04-26',NULL,'Male','PRACT9','Gustavo',NULL,NULL,'Doctor of Medicine','Fring',NULL,2),
(12,'',NULL,'1963-08-08',NULL,'Unknown','PRACT10','Hector',NULL,NULL,'Emergency Medical Tehnician','Salamanca',NULL,7),
(13,'',NULL,'1966-03-20','minerals@gmail.com','Male','PRACT11','Hank','',NULL,'Doctor of Medicine','Schrader','',8),
(14,'','Freedom trail 12','1970-07-11',NULL,'Female','PRACT12','Skyler',NULL,NULL,'Certified Nurse Midwife','White',NULL,6),
(15,'',NULL,'1960-11-12','callsaul@gmail.com','Male','PRACT13','Saul','mcgill',NULL,'Doctor of Medicine','Goodman','goodman',3),
(16,'',NULL,'1987-03-12',NULL,'Female','PRACT14','Jane',NULL,NULL,'Nurse Practitioner','Margolis',NULL,5),
(17,'',NULL,'1961-07-09',NULL,'Male','PRACT15','Alberto',NULL,'22355518','Doctor of Medicine','Salamanca',NULL,7);


/*Data for the table `patient` */

insert  into `patient`(`id`,`active`,`adress`,`birth_date`,`deceased`,`email`,`gender`,`identifier`,`maritial_status`,`name`,`phone`,`surname`,`custodian_organization_id`,`primary_practitioner_id`) values 
(1,'',NULL,'2020-06-25',NULL,NULL,NULL,'PAT#1',NULL,'Robert',NULL,'House',7,7),
(2,'','Mars avenue 123','1986-08-08',NULL,NULL,'Male','PAT#2','Divorced','Edward',NULL,'Sallow',7,17),
(3,'',NULL,'1982-07-12','\0',NULL,'Male','PAT#3','Polygamous','Donald',NULL,'Kimball',2,11),
(4,'','Old town road 3','1995-04-04',NULL,NULL,'Female','PAT#4','Never married','Veronica',NULL,'Santiago',8,13),
(5,'',NULL,'1994-06-12',NULL,'cassrose94@gmail.com','Female','PAT#5','Annulled','Rose',NULL,'Cassidy',2,11),
(6,'',NULL,'1988-01-03',NULL,NULL,'Other','PAT#6','Polygamous','James',NULL,'Barnes',3,15),
(7,'',NULL,'1999-04-01',NULL,NULL,'Male','PAT#7',NULL,'Bartholomew',NULL,'Simpson',7,7),
(9,'',NULL,'2001-09-22',NULL,NULL,'Female','PAT#8',NULL,'Lisa',NULL,'Simpson',7,7),
(10,'',NULL,'2006-03-03',NULL,NULL,'Female','PAT#9',NULL,'Margaret',NULL,'Simpson',7,17);


/*Data for the table `examination` */

insert  into `examination`(`id`,`diagnosis`,`end_date`,`identifier`,`priority`,`start_date`,`status`,`organization_id`,`patient_id`,`service_type_id`) values 
(1,NULL,'2022-06-16','EXAM1','routine','2022-06-16','planned',7,9,14),
(2,NULL,NULL,'EXAM2','asap',NULL,'in-progress',2,1,16),
(3,'Patient is recovering','2022-02-04','EXAM3','asap','2022-02-03','finished',7,1,4),
(4,NULL,NULL,'EXAM4','timing critical',NULL,'planned',2,9,6),
(6,NULL,NULL,NULL,'callback results',NULL,'triaged',7,2,18),
(7,NULL,NULL,NULL,'asap',NULL,'triaged',3,3,17);

/*Data for the table `examination_practitioner` */

insert  into `examination_practitioner`(`id`,`examination_id`,`practitioner_id`) values 
(1,1,17),
(2,1,7),
(3,2,11),
(4,3,17),
(5,4,11),
(8,6,17),
(9,6,7),
(10,7,15);



