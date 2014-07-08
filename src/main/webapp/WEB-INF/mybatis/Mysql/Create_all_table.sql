-- Free Board 게시판 테이블

CREATE TABLE `Board` (
  `boardNum` smallint(10) NOT NULL AUTO_INCREMENT,
  `groupNum` smallint(10) DEFAULT NULL,
  `boardTitle` varchar(100) NOT NULL,
  `boardContents` text NOT NULL,
  `commentCount` smallint(6) DEFAULT '0',
  `member_num` smallint(6) DEFAULT NULL,
  `boardWriter` varchar(20) NOT NULL,
  `boardDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `boardViews` smallint(10) NOT NULL,
  `step` smallint(10) NOT NULL,
  `level` smallint(10) NOT NULL,
  `boardPass` varchar(20) NOT NULL,
  PRIMARY KEY (`boardNum`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;



-- Java 게시판 테이블

CREATE TABLE `Java` (
  `boardNum` smallint(10) NOT NULL AUTO_INCREMENT,
  `groupNum` smallint(10) DEFAULT NULL,
  `boardTitle` varchar(100) NOT NULL,
  `boardContents` text NOT NULL,
  `commentCount` smallint(6) DEFAULT '0',
  `member_num` smallint(6) DEFAULT NULL,
  `boardWriter` varchar(20) NOT NULL,
  `boardDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `boardViews` smallint(10) NOT NULL,
  `step` smallint(10) NOT NULL,
  `level` smallint(10) NOT NULL,
  `boardPass` varchar(20) NOT NULL,
  PRIMARY KEY (`boardNum`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;



-- Javascript 게시판 테이블

CREATE TABLE `Javascript` (
  `boardNum` smallint(10) NOT NULL AUTO_INCREMENT,
  `groupNum` smallint(10) DEFAULT NULL,
  `boardTitle` varchar(100) NOT NULL,
  `boardContents` text NOT NULL,
  `commentCount` smallint(6) DEFAULT '0',
  `member_num` smallint(6) DEFAULT NULL,
  `boardWriter` varchar(20) NOT NULL,
  `boardDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `boardViews` smallint(10) NOT NULL,
  `step` smallint(10) NOT NULL,
  `level` smallint(10) NOT NULL,
  `boardPass` varchar(20) NOT NULL,
  PRIMARY KEY (`boardNum`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;



-- Oracle 게시판 테이블

CREATE TABLE `Oracle` (
  `boardNum` smallint(10) NOT NULL AUTO_INCREMENT,
  `groupNum` smallint(10) DEFAULT NULL,
  `boardTitle` varchar(100) NOT NULL,
  `boardContents` text NOT NULL,
  `commentCount` smallint(6) DEFAULT '0',
  `member_num` smallint(6) DEFAULT NULL,
  `boardWriter` varchar(20) NOT NULL,
  `boardDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `boardViews` smallint(10) NOT NULL,
  `step` smallint(10) NOT NULL,
  `level` smallint(10) NOT NULL,
  `boardPass` varchar(20) NOT NULL,
  PRIMARY KEY (`boardNum`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;




-- bbsinformation, 각 게시판의 디자인 정보
CREATE TABLE `bbsinformation` (
  `bbsNum` tinyint(4) NOT NULL AUTO_INCREMENT,   -- 게시판 고유 번호
  `bbsName` varchar(20) NOT NULL,                      -- 게시판의 이름, 데이터베이스의 실제 테이블명과 일치해야 한다.
  `pageCount` tinyint(4) NOT NULL,                        -- 게시판 하단의 page 네비게이션에서 표시할 페이지 숫자          
  `LimitCount` tinyint(4) NOT NULL,                        -- 한 개의 page당 표시해야 하는 게시물의 갯수
  `previewCount` tinyint(4) NOT NULL,                    -- 메인페이지에서 해당 게시판의 불러올 최신 게시물 갯수 
  `bbsStyle` varchar(20) NOT NULL,                       -- 게시판 스타일, 아직 orange밖에 없으므로 orange로 생성할 것.
  PRIMARY KEY (`bbsNum`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

insert into bbsinformation(bbsName, pageCount, LimitCount, previewCount, bbsStyle) values('Board', 10, 15, 5, 'orange'); 
insert into bbsinformation(bbsName, pageCount, LimitCount, previewCount, bbsStyle) values('Java', 10, 15, 5, 'orange');
insert into bbsinformation(bbsName, pageCount, LimitCount, previewCount, bbsStyle) values('Javascript', 10, 15, 5, 'orange');
insert into bbsinformation(bbsName, pageCount, LimitCount, previewCount, bbsStyle) values('Oracle', 10, 15, 5, 'orange');



-- 댓글 테이블

CREATE TABLE `comment` (
  `commentNum` int(10) NOT NULL AUTO_INCREMENT,
  `bbsName` varchar(10) DEFAULT NULL,
  `boardNum` int(10) NOT NULL,
  `commentContents` varchar(300) NOT NULL,
  `commentWriter` varchar(20) NOT NULL,
  `writerNum` int(11) NOT NULL,
  `commentDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`commentNum`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;



-- 첨부파일 테이블

CREATE TABLE `file` (
  `fileNum` int(10) NOT NULL AUTO_INCREMENT,
  `boardNum` int(10) DEFAULT NULL,
  `bbsName` varchar(20) DEFAULT NULL,
  `fileName` varchar(200) NOT NULL,
  `fileSize` varchar(10) DEFAULT NULL,
  `storeDate` date DEFAULT NULL,
  `thumbUrl` varchar(200) DEFAULT NULL,
  `encodedFileName` varchar(200) NOT NULL,
  `encodedURL` varchar(200) NOT NULL,
  PRIMARY KEY (`fileNum`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;




-- 회원등급 테이블

CREATE TABLE `grade` (
  `CODE` char(1) CHARACTER SET latin1 NOT NULL,
  `NAME` varchar(10) NOT NULL,
  `LOW_POINT` mediumint(11) DEFAULT NULL,
  `HIGH_POINT` mediumint(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 포인트 구현은 제대로 되지 않았음.
-- 어드민 권한을 가지려면 회원 테이블의 point를 백만점으로 수정한다. 

insert into grade(CODE, NAME, LOW_POINT, HIGH_POINT) values('A', 'IRON', 0, 999);
insert into grade(CODE, NAME, LOW_POINT, HIGH_POINT) values('B', 'BRONZE', 1000, 1999);
insert into grade(CODE, NAME, LOW_POINT, HIGH_POINT) values('C', 'SILVER', 2000, 2999);
insert into grade(CODE, NAME, LOW_POINT, HIGH_POINT) values('D', 'GOLD', 3000, 3999);
insert into grade(CODE, NAME, LOW_POINT, HIGH_POINT) values('E', 'DIAMOND', 4000, 4999);
insert into grade(CODE, NAME, LOW_POINT, HIGH_POINT) values('F', 'SUPERVISOR', 1000000, 1000000);



-- 회원 테이블

CREATE TABLE `members` (
  `MEMBER_NUM` smallint(11) NOT NULL AUTO_INCREMENT,
  `MEMBER_ID` varchar(20) NOT NULL,
  `PASSWORD` varchar(100) NOT NULL,
  `EMAIL` varchar(100) NOT NULL,
  `NICKNAME` varchar(10) DEFAULT NULL,
  `NAME` varchar(20) DEFAULT NULL,
  `BIRTHYEAR` varchar(4) DEFAULT NULL,
  `BIRTHMONTH` varchar(2) DEFAULT NULL,
  `BIRTHDAY` varchar(2) DEFAULT NULL,
  `ADDRESS` varchar(100) DEFAULT NULL,
  `PHONE` varchar(13) DEFAULT NULL,
  `POINT` mediumint(6) DEFAULT '0',
  PRIMARY KEY (`MEMBER_NUM`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


