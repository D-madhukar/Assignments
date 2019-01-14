a)
    create table student(id numeric(16),
    name varchar(50),
    email varchar(100),
    primary key (id));

    create table course(course_id numeric(4),
    course_name varchar(20),
    course_fee numeric(10,0),
    primary key(course_id));

    create table professor(professor_id numeric(16),
    professor_name varchar(50),
    course_id numeric(4),
    foreign key (course_id) references course (course_id),
    primary key (professor_id));

    create table enrollment(stud_id numeric(16),
    course_id numeric(4),
    foreign key (stud_id) references student(id),
    foreign key(course_id) references course(course_id));
                                             
    Student Table
    +-----+--------+----------------------+
    | id  | name   | email                |
    +-----+--------+----------------------+
    | 100 | madhu  | madhukar@gmail.com   |
    | 101 | madhav | madhav@gmail.com     |
    | 102 | lucky  | luckyemail@gmail.com |
    | 103 | dev    | devc++@gmail.com     |
    +-----+--------+----------------------+
    Course Table
    +-----------+-------------+------------+
    | course_id | course_name | course_fee |
    +-----------+-------------+------------+
    |      1900 | JAVA        |          0 |
    |      1901 | C++         |          0 |
    |      1902 | ANGULAR     |          0 |
    |      1903 | J2EE        |          0 |
    +-----------+-------------+------------+
    Professors Table
    +--------------+----------------+-----------+
    | professor_id | professor_name | course_id |
    +--------------+----------------+-----------+
    |         1601 | madhusudhan    |      1901 |
    |         1602 | vivek          |      1902 |
    |         1603 | venugoapl      |      1900 |
    |         1604 | karthik        |      1903 |
    +--------------+----------------+-----------+
    Enrollment Table
    +---------+-----------+
    | stud_id | course_id |
    +---------+-----------+
    |     100 |      1900 |
    |     100 |      1902 |
    |     100 |      1903 |
    |     101 |      1902 |
    |     101 |      1901 |
    |     102 |      1900 |
    |     102 |      1902 |                              
    |     103 |      1902 |
    |     103 |      1900 |
    +---------+-----------+
b)
    1)
        select s.id,s.name,c.course_id,c.course_name fromstudent s,
        enrollment e,course c where s.id=e.stud_id and e.course_id=c.course_id and s.id=101$$
        +-----+--------+-----------+-------------+
        | id  | name   | course_id | course_name |
        +-----+--------+-----------+-------------+
        | 101 | madhav |      1902 | ANGULAR     |
        | 101 | madhav |      1901 | C++         |
        +-----+--------+-----------+-------------+
    2)
        select course_name,professor_name from professor p INNER JOIN course c where p.course_id=c.course_id and 
        c.course_id=(select course_id from enrollment group by course_id order by count(course_id) desc limit 1);
        +-------------+----------------+
        | course_name | professor_name |
        +-------------+----------------+
        | ANGULAR     | vivek          |
        +-------------+----------------+
                        4

c)
    CREATE DEFINER=`root`@`localhost` PROCEDURE `getDetails`(IN id numeric(16))
    BEGIN
        DECLARE p_id numeric(16);
        DECLARE p_name varchar(50);
        DECLARE loop_controller INT DEFAULT 0;
        DECLARE prof_cursor CURSOR FOR (select professor_id,professor_name from professor where course_id 
        in (select course_id from enrollment where stud_id=id));
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET loop_controller = 1;
        open prof_cursor;
        start_loop:LOOP
        fetch prof_cursor into p_id,p_name;
        IF loop_controller=1 THEN
                LEAVE start_loop;
            END IF; 
        insert into temp values(p_id,p_name);
        END LOOP;  
    END$$

    mysql>call getDetails(103);
    for student_id=103;
    +--------------+----------------+
    | professor_id | professor_name |
    +--------------+----------------+
    |         1602 | vivek          |
    |         1603 | venugoapl      |
    +--------------+----------------+




