-- 메인 카테고리 생성
insert into main_category(code_name)
VALUES ("IT");
insert into main_category(code_name)
VALUES ("운동");
-- 서브 카테고리 생성
insert into sub_category(code_name,main_code)
VALUES ("스프링부트",1);
insert into sub_category(code_name,main_code)
VALUES ("리액트",1);
insert into sub_category(code_name,main_code)
VALUES ("자바",1);
insert into sub_category(code_name,main_code)
VALUES ("자바스크립트",1);
insert into sub_category(code_name,main_code)
VALUES ("헬스",2);
insert into sub_category(code_name,main_code)
VALUES ("허리",2);
-- 뱃지 생성
insert into badge_category(badgeName)
VALUES ("GPT");
insert into badge_category(badgeName)
VALUES ("정리");
insert into badge_category(badgeName)
VALUES ("내가쓴글");

