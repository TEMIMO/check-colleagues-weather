CREATE TABLE public.cw_weather (
        weather_id numeric NOT NULL DEFAULT nextval('cw_weather_weather_id_seq'::regclass),
        i_temperature int4 NOT NULL,
        s_description varchar(200) NOT NULL,
        d_updated timestamptz NULL,
        i_update_status int4 NULL,
        colleague_id int8 NULL,
        CONSTRAINT cw_weather_pkey PRIMARY KEY (weather_id)
);
CREATE INDEX fki_colleague_id_to_weather_fkey ON public.cw_weather USING btree (colleague_id);

ALTER TABLE public.cw_weather ADD CONSTRAINT colleague_id_to_weather_fkey FOREIGN KEY (colleague_id) REFERENCES public.cw_colleague(colleague_id);