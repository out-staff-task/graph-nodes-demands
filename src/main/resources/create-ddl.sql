create table public.spikes
(
	id bigint not null
		constraint spikes_pk
		primary key,
	parent_id bigint
		constraint spikes_uc
		references spikes
);
