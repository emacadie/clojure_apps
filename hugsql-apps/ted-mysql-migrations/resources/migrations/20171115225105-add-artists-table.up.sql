create table artists (
    name        varchar( 256 ) NOT NULL,
    mb_id       binary( 16 )   NOT NULL,
    date_formed date           NOT NULL,
    PRIMARY KEY ( name )
);

-- to insert mb_id: UUID_TO_BIN(UUID())
-- to select: SELECT BIN_TO_UUID( mb_id ) FROM artists

