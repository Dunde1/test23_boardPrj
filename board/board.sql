drop table board;
create table board (
	num  int  not  null,
	writer   varchar(50),
	email   varchar(30),	
	subject   varchar(100),	
	passwd   varchar(10),
	reg_date   DATE,
	readcount  int,
	ref   int,
	re_step   int,
	re_level  int,
	content    varchar(3000),
	ip   varchar(15)
);

ALTER TABLE board
       ADD   PRIMARY KEY (num)  ;

drop Sequence seq_log;
Create Sequence seq_log
start with 1
increment by 1
maxvalue 10000000;

/*insert into board values(seq_log.nextval,'3','add','5','8');
commit;
*/