****SCHOOL RECORD SYSTEM******

-The program launches properly without database but you'll postgresql database inorder to manage records

-Inorder to run the program properly you must have postgresql installed
	-If you don't have postgresql installed,
		You can download postgresql from "https://get.enterprisedb.com/postgresql/postgresql-11.2-2-windows-x64.exe"
- Next you'll need to create a server with a name of your choice on localhost with password "123456" (Check screenshots)
- After creating server, create a database with name "SchoolRecordSystem" (Check screenshots)
- Finally open the query tool and copy and paste the following sql script and execute the query
- And that's it, your app is ready for use


		-- Database: SchoolRecordSystem

		-- DROP DATABASE "SchoolRecordSystem";

		CREATE TABLE public.admin (

		    admin_name character varying(64),
		    admin_password character varying(64),
		    CONSTRAINT admin_pkey PRIMARY KEY (admin_name),
		    CONSTRAINT admin_name UNIQUE (admin_name)

		    DEFERRABLE INITIALLY DEFERRED
		);

		INSERT INTO public.admin(admin_name, admin_password)
		VALUES ('admin', 'admin');

		ALTER TABLE public.admin
		    OWNER to postgres;

		CREATE TABLE public.course (

		    course_id SERIAL,
		    course_credit integer,
		    course_code character varying(10),
		    course_title character varying(64),
		    course_semester integer NOT NULL,
		    CONSTRAINT course_pk PRIMARY KEY (course_id),
		    CONSTRAINT course_unique UNIQUE (course_code)

		);

		ALTER TABLE public.course
		    OWNER to postgres;

		CREATE TABLE public.mycourses (

		    mycourses_id SERIAL,
		    mycourses_matricule character varying(10),
		    mycourses_code character varying(64),
		    mycourses_title character varying(64),
		    mycourses_credit double precision,
		    mycourses_ca double precision,
		    mycourses_exams double precision,
		    mycourses_total double precision,
		    mycourses_semester integer,
		    CONSTRAINT mycourse_pkey PRIMARY KEY (mycourses_id)
		);

		ALTER TABLE public.mycourses
		    OWNER to postgres;   

		CREATE TABLE public.school (

		    school_id SERIAL,
		    school_faculty character varying(64) NOT NULL,
		    school_department1 character varying(64) NOT NULL,
		    school_department2 character varying(64),
		    school_department3 character varying(64),
		    CONSTRAINT school_pkey PRIMARY KEY (school_id)
		);

		ALTER TABLE public.school
		    OWNER to postgres;

		INSERT INTO public.school(school_faculty,school_department1) 
		VALUES('Faculty of Engineering', 'CEN');

		CREATE TABLE public.student (

		    student_id SERIAL,
		    student_fname character varying(64) NOT NULL,
		    student_lname character varying(64),
		    student_matricule character varying(10),
		    student_password character varying(64) DEFAULT 'password'::character varying,
		    student_faculty character varying(64),
		    student_department character varying(64),
		    student_email character varying(64),
		    student_phone character varying(64),
		    student_dob character varying(64),
		    student_picture character varying,
		    CONSTRAINT student_pkey PRIMARY KEY (student_id)
		);

		ALTER TABLE public.student
		    OWNER to postgres;

		CREATE TABLE public.studentgpa (

		    studentgpa_id SERIAL,
		    studentgpa_matricule character varying(10) DEFAULT 'NULL'::character varying,
		    studentgpa_1thsem double precision DEFAULT 0.00,
		    studentgpa_2thsem double precision DEFAULT 0.00,
		    studentgpa_3thsem double precision DEFAULT 0.00,
		    studentgpa_4thsem double precision DEFAULT 0.00,
		    studentgpa_5thsem double precision DEFAULT 0.00,
		    studentgpa_6thsem double precision DEFAULT 0.00,
		    studentgpa_7thsem double precision DEFAULT 0.00,
		    studentgpa_8thsem double precision DEFAULT 0.00,
		    studentgpa_9thsem double precision DEFAULT 0.00,
		    studentgpa_10thsem double precision DEFAULT 0.00,
		    studentgpa_11thsem double precision DEFAULT 0.00,
		    studentgpa_12thsem double precision DEFAULT 0.00,
		    CONSTRAINT studentgpa_pk PRIMARY KEY (studentgpa_id)
		);

		ALTER TABLE public.studentgpa
		    OWNER to postgres;

		CREATE TABLE public.tcourses (

		    tcourses_id SERIAL,
		    tcourses_matricule character varying(10) NOT NULL,
		    tcourses_code character varying(64) NOT NULL,
		    tcourses_title character varying(64) NOT NULL,
		    tcourses_credit integer NOT NULL,
		    CONSTRAINT tcourses_pkey PRIMARY KEY (tcourses_id)
		);

		ALTER TABLE public.tcourses
		    OWNER to postgres;

		CREATE TABLE public.teacher (

		    teacher_id SERIAL,
		    teacher_fname character varying(64) NOT NULL,
		    teacher_lname character varying(64),
		    teacher_matricule character varying(10) NOT NULL,
		    teacher_email character varying(64),
		    teacher_password character varying(64) NOT NULL DEFAULT 'password'::character varying,
		    teacher_department character varying(10),
		    teacher_course character varying(64) NOT NULL,
		    CONSTRAINT teacher_pkey PRIMARY KEY (teacher_id)
		);

		ALTER TABLE public.teacher
		    OWNER to postgres;
