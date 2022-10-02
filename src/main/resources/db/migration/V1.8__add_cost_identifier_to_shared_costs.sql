ALTER TABLE COST_SHARE
    ADD cost_identifier UUID;

UPDATE COST_SHARE
SET cost_identifier = (SELECT identifier FROM COST WHERE id = cost);

alter table cost_share
    drop constraint cost_share_cost_fkey;

alter table cost_share
    drop column cost;

ALTER TABLE cost
    ADD CONSTRAINT cost_identifier_uq UNIQUE (identifier);

alter table cost_share
    add constraint shared_cost_identifier_fk
        foreign key (cost_identifier) references public.cost (identifier);