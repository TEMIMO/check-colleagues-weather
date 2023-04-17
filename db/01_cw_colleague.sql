CREATE TABLE public.cw_colleague (
     s_name varchar(200) NOT NULL,
     s_job_title varchar(50) NOT NULL,
     d_birthday timestamp NOT NULL,
     s_city varchar(50) NOT NULL,
     colleague_id bigserial NOT NULL,
     CONSTRAINT cw_colleague_pkey PRIMARY KEY (colleague_id)
);