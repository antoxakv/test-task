create table CONTACT
(
   CONTACT_ID identity
);

create table APPLICATION
(
   APPLICATION_ID identity,
   DT_CREATED TIMESTAMP not null,
   PRODUCT_NAME varchar(255) not null,
   CONTACT_ID bigint not null,
   foreign key (CONTACT_ID) references CONTACT(CONTACT_ID)
);