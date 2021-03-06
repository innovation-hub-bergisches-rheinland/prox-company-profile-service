create table company (id uuid not null, location varchar(255), founding_date varchar(255), homepage varchar(255), name varchar(255), number_of_employees varchar(255), vita varchar(255), company_logo_id uuid, primary key (id));
create table company_branches (company_id uuid not null, branch_name varchar(255));
create table company_languages (company_id uuid not null, languages_id uuid not null, primary key (company_id, languages_id));
create table company_quarters (company_id uuid not null, location varchar(255));
create table company_logo (id uuid not null, content_id uuid, content_length int8, mime_type varchar(255), primary key (id));
create table country (id uuid not null, english_name varchar(255), german_name varchar(255), iso_identifier2 varchar(2), primary key (id));
create table language (id uuid not null, english_name varchar(255), german_name varchar(255), iso_identifier2 varchar(2), type int4 not null, primary key (id));
create table location (id uuid not null, coordiantes varchar(255) not null, locode varchar(3) not null, name varchar(255) not null, name_without_diacritics varchar(255) not null, country_id uuid not null, primary key (id));
alter table country add constraint UK_2vfxe8sd8nhvd1lpn6pvw147x unique (iso_identifier2);
alter table language add constraint UK_ox5svkhkvot75m4c12xvy7sko unique (iso_identifier2);
alter table location add constraint UK_ndmw96jtvo3mq70rcdifvhmbi unique (locode);
alter table company add constraint FKj5vmwp7ppnh6drhyawkx2h6o foreign key (company_logo_id) references company_logo;
alter table company_branches add constraint FKisn84t8fa2r4bid45ucdux2qj foreign key (company_id) references company;
alter table company_languages add constraint FKrqod7lgl1v96un6kok9906iop foreign key (languages_id) references language;
alter table company_languages add constraint FKf049v9qaxxjpc7vhph4hp8t0o foreign key (company_id) references company;
alter table company_quarters add constraint FKe3g0gkgljwtl8oypmvdbxqdyw foreign key (company_id) references company;
alter table location add constraint FKn5m6ve3ryy2r25qvisdrg0aos foreign key (country_id) references country;


INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('54379a94-804a-43b6-b194-c27a896917b9','aa','Afar','Afar',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('69053852-6485-4a67-b83f-1917b8a9479d','ab','Abchasisch','Abkhazian',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('80d762a1-86eb-4f8e-bf73-d9014d509fea','af','Afrikaans','Afrikaans',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('3d380f91-e62e-4eb5-9ad9-8a4feb364e33','ak','Akan','Akan',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('e81453e6-d4bd-4aee-bfec-da51d75379ac','am','Amharisch','Amharic',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('64bd052b-bef1-4f51-95ee-3c934f59bf73','ar','Arabisch','Arabic',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('d901e190-73e1-431b-b0c4-7354eb2a5835','an','Aragonesisch','Aragonese',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('ce287d82-b769-4d6a-bc40-bdaed220edc3','as','Assamesisch','Assamese',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('50839bb1-7f6c-4382-a945-ea42ee7ffe7a','av','Awarisch','Avaric',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('aefe2414-c67f-44b0-b75f-c15d6ebd961d','ae','Avestisch','Avestan',0);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('93b9767f-da4f-4087-884d-46260886f311','ay','Aymara','Aymara',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('71a91b7c-669a-4cd7-b5fe-b6af7cea8da5','az','Aserbaidschanisch','Azerbaijani',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('62bd919a-30d9-43c4-96cf-704ee108f7aa','ba','Baschkirisch','Bashkir',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('d9ec42cf-942d-40d4-bc8b-1b2a20efec27','bm','Bambara','Bambara',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('f0305a9b-e73e-40f2-878e-9eedb30a536c','be','Wei??russisch','Belarusian',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('e601a43d-e4b2-4bde-86ae-dda5f3d0890e','bn','Bengalisch','Bengali',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('97099164-6428-4de3-93f4-752226a7a69e','bh','Bihari','Bihari languages',5);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('3ad04dcd-4e70-45a6-b19c-4738c3c53167','bi','Bislama','Bislama',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('6e38c9e2-48dd-42fe-882f-f2faafd3d91b','bo','Tibetisch','Tibetan',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('4df77ee2-da65-4f2c-89e7-d1c9d5407a85','bs','Bosnisch','Bosnian',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('e1db9089-584d-4d30-96e0-d80823178823','br','Bretonisch','Breton',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('25e7d67c-5b1c-4c1b-965b-77e34da38265','bg','Bulgarisch','Bulgarian',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('00a4dfff-0bd8-40e3-acdb-3447b18444ad','ca','Katalanisch','Catalan',5);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('f9a0f350-d913-4aa0-82aa-d72eee5dabd4','cs','Tschechisch','Czech',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('4756726a-55f4-44d6-9e7a-1f15c610b50d','ch','Chamorro','Chamorro',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('c757be7b-d671-476c-96ec-663ec4173a09','ce','Tschetschenisch','Chechen',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('f3467c94-1054-4be5-b503-a9515dece149','cu','Kirchenslawisch','Church Slavic',5);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('27465e98-e4b0-46ff-897f-3bf673ed8c8e','cv','Tschuwaschisch','Chuvash',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('a8283136-4cc5-437d-b05a-63ef902813e5','kw','Kornisch','Cornish',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('89e6451e-8785-4151-b274-7375b07beb7f','co','Korsisch','Corsican',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('d730d1c8-8938-4cf6-b648-e7705f7b5cc8','cr','Cree','Cree',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('885ecca1-5ceb-45d8-bef6-0e9e5435e5b9','cy','Walisisch','Welsh',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('eda83604-ad7c-42c7-8eee-faf9112fd0fd','da','D??nisch','Danish',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('c3ecfa87-9e48-480d-a3d8-f27984c46537','de','Deutsch','German',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('143590f7-5b36-460c-8c52-cdf977043a1b','dv','Dhivehi','Divehi',5);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('2adc0dac-4072-478f-a813-bbde0703004a','dz','Dzongkha','Dzongkha',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('4b6e5230-63f8-4a57-a4df-ebf60fdbe22d','el','Griechisch','Greek',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('92533481-f92c-4fa5-8ff0-4d1e254b3a53','en','Englisch','English',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('4cb1a2d2-7d1f-4cb2-bc75-85cedd8cdafb','eo','Esperanto','Esperanto',2);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('0a060a05-9be3-4f17-b760-560cbab0c025','et','Estnisch','Estonian',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('68134c49-dfb3-4fe1-b051-3db916917123','eu','Baskisch','Basque',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('768d8c2f-2e09-4c9f-ab53-34089dd51c8c','ee','Ewe','Ewe',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('82520462-70ba-4637-b9d5-6ef23f668263','fo','F??r??isch','Faroese',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('cf7843c7-a5c6-49a1-b0de-9096513878b0','fa','Persisch','Persian',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('353be7eb-aa34-4860-a0c8-6876db8c247f','fj','Fidschi','Fijian',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('6d2ee4af-70a5-4308-a5ad-e659a926d8b2','fi','Finnisch','Finnish',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('65b4d49c-b34d-42bb-8874-d315b6e0ce71','fr','Franz??sisch','French',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('e66577a1-306a-4e0e-a8e2-d6d35c887a52','fy','Westfriesisch','Western Frisian',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('a4741a2b-209e-4a2f-84a4-922d3112c3b7','ff','Fulfulde','Fulah',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('42a65711-7c74-45c1-ae88-bf5ab9d5e30e','gd','Schottisch-g??lisch','Gaelic',5);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('29c69cea-c95c-44c3-819f-29ee7481cb67','ga','Irisch','Irish',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('9cef4273-b896-42bc-b533-2891a70502c1','gl','Galicisch','Galician',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('33908800-e86c-4f42-adc7-f89fa5841ca4','gv','Manx','Manx',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('d38ae215-c477-4f47-9376-b8d4f6fe56bf','gn','Guarani','Guarani',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('bb1be686-46c6-4091-988a-403c06357189','gu','Gujarati','Gujarati',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('fe87212e-9116-4498-95fd-c14c0db1bf0d','ht','Haitianisch','Haitian',5);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('5d41aacf-f709-499f-ac57-6dce68144753','ha','Hausa','Hausa',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('05b5e909-4d57-4500-83fd-bda907125ef4','he','Hebr??isch','Hebrew',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('765c701a-be59-410a-b0e5-a21306da1708','hz','Otjiherero','Herero',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('644b0e81-f2fa-46fb-9a9b-c2499ae0512a','hi','Hindi','Hindi',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('79d65b33-e384-43f9-aee2-149b0ae6d728','ho','Hiri Motu','Hiri Motu',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('2f2c6e10-d300-4576-9d60-81066392a1ad','hr','Kroatisch','Croatian',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('be247af1-ff91-4030-9520-6889dd52c627','hu','Ungarisch','Hungarian',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('784c7907-d23c-416e-94c3-808ade6fc133','hy','Armenisch','Armenian',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('e2da0406-2861-4194-bbd9-f002bf8f665f','ig','Igbo','Igbo',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('da4cc438-ebe9-4f9e-a021-b4513538fbfb','io','Ido','Ido',2);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('c449b25b-fb62-417a-90e4-e02d3c09be18','ii','Yi','Sichuan Yi',5);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('dc01101c-e774-43c7-887e-1e8052a0884d','iu','Inuktitut','Inuktitut',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('716f08dd-a6ed-4dac-839a-1b5f957f3444','ie','Interlingue','Interlingue',5);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('0c605c4a-118c-4244-b7c3-699ac4b73a35','ia','Interlingua','Interlingua (International Auxiliary Language Association)',2);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('9920574b-868c-4463-ac58-df7334bc5545','id','Indonesisch','Indonesian',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('7a2326dc-ee18-43c1-836a-4172389c102f','ik','Inupiaq','Inupiaq',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('cd8516df-3e17-4a06-b55f-6a0e61c5c64f','is','Isl??ndisch','Icelandic',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('178b3242-9fe1-4b2c-ad0a-0592791e947c','it','Italienisch','Italian',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('bb9b7a64-2e6a-462f-b453-69fec9295eca','jv','Javanisch','Javanese',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('b24625b5-c448-4a71-b4b0-6b7bcb4379c9','ja','Japanisch','Japanese',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('9fdb3e32-4aae-41e3-a8fe-f661e908a7e2','kl','Gr??nl??ndisch','Kalaallisut',5);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('eb169f52-14bf-4d68-bffc-6bbcf49f36ca','kn','Kannada','Kannada',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('51490e8d-7618-4edc-ae41-2eb438130ec9','ks','Kashmiri','Kashmiri',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('9a1cce18-be69-4360-9a88-e24648ade75d','ka','Georgisch','Georgian',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('9acfd77e-c442-47a7-a549-fe64609195a4','kr','Kanuri','Kanuri',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('cf0e31ef-6f5a-45c6-8a31-ea982c777173','kk','Kasachisch','Kazakh',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('66961c92-bf1d-459b-b7ea-a5d75ec5851f','km','Khmer','Central Khmer',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('bf86ad61-5c0f-45d1-9e0b-3ec6714593a1','ki','Kikuyu','Kikuyu',5);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('28f5d97b-d3e0-47ba-bd5b-83a75ba095ba','rw','Kinyarwanda','Kinyarwanda',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('e8b19efb-c4e2-4796-915c-fc2834c459f7','ky','Kirgisisch','Kirghiz',5);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('43535b6c-aa1e-451e-b861-f6cd80a55b1a','kv','Komi','Komi',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('fdf32052-991b-422b-be1f-936e9aea97cf','kg','Kikongo','Kongo',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('53e0685c-203c-47cb-840f-38a88fb919c9','ko','Koreanisch','Korean',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('0226aee2-5e59-4d91-8c8b-630788798db1','kj','oshiKwanyama','Kuanyama',5);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('8855ceb1-815c-47f6-bdef-a074b5473169','ku','Kurdisch','Kurdish',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('fca49ff8-c080-413c-b4ee-b6cbc331e5ac','lo','Laotisch','Lao',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('2fcc4ca4-0807-40af-8eda-885d97234eb8','la','Latein','Latin',0);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('b97713da-7100-44a6-a53b-4e163923107d','lv','Lettisch','Latvian',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('13c89d45-407d-40e6-a9ea-34a77a0cd0da','li','Limburgisch','Limburgan',5);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('1c746317-27b5-4c27-ad4a-75eff76b1ad9','ln','Lingala','Lingala',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('86b685af-7d5b-4b3d-87bd-59532257a4c1','lt','Litauisch','Lithuanian',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('31e59199-2689-4834-9ea6-42c13de81662','lb','Luxemburgisch','Luxembourgish',5);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('7e18b7aa-8154-49e6-9083-cc86f8c966e0','lu','Kiluba','Luba-Katanga',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('e7679ee2-1e9a-455a-b29a-daf2e17a5aba','lg','Luganda','Ganda',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('93931360-9973-426f-bf9b-792d539b48ab','mh','Marshallesisch','Marshallese',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('416624c9-438c-4584-9b15-ae65c1a993f4','ml','Malayalam','Malayalam',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('317b9adb-2605-4440-adf8-ea333af6b0b8','mi','Maori','Maori',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('11252c1b-ba10-487c-9fdc-f9f28dc3637d','mr','Marathi','Marathi',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('66e9499f-ecbb-493e-ab9b-6612f710d940','mk','Mazedonisch','Macedonian',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('1bda639e-6306-47e6-894f-fa4a031d4347','mg','Malagasy','Malagasy',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('9c2131ea-c70c-4c78-83cf-8625a9ad973b','mt','M0esisch','M0ese',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('9c472f7f-c050-43ea-9e76-ac6f0f5a2c1d','mn','Mongolisch','Mongolian',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('906d255a-1782-4cbb-83db-8d3a830799f4','ms','Malaiisch','Malay',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('3b2b2e62-f0ce-4376-b4f6-4a7d2733f719','my','Birmanisch','Burmese',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('97125e1c-5863-4c54-b60a-2a2711b7dd15','na','Nauruisch','Nauru',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('8dc56e35-01c4-495d-be2c-3c64ed82c35b','nv','Navajo','Navajo',5);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('f8cf317f-8c25-4b54-899e-4d9902b4fcfb','nr','S??d-Ndebele','Ndebele',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('11551115-ed8f-44df-b464-030f9136933a','nd','Nord-Ndebele','Ndebele',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('7d7e2ed5-a639-4b95-b4ee-0e7411fd5140','ng','Ndonga','Ndonga',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('64b0b622-4c89-42c1-b337-ce1d72180278','ne','Nepali','Nepali',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('b67a79af-1b7b-477e-a5f5-0743b3b16e41','nl','Niederl??ndisch','Dutch',5);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('27a57789-857e-4be9-a29a-8d40c77a13a4','nn','Nynorsk','Norwegian Nynorsk',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('b2799206-68a2-408a-86b8-5c731ae669b8','nb','Bokmal','Bokmal',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('2b4db4ef-d025-4df6-9b75-2dc26f620ca5','no','Norwegisch','Norwegian',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('c528f9a7-7246-4e84-b4d4-ef3c5e17fed2','ny','Chichewa','Chichewa',5);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('85c45a9b-9373-474e-86c7-f1d165ee877b','oc','Okzitanisch','Occitan',5);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('a0bffe51-f01e-4df2-82bd-336dcaca2f10','oj','Ojibwe','Ojibwa',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('6b2feead-d477-4c01-904a-42558c0d5438','or','Oriya','Oriya',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('45ea7f4e-4d3c-4c01-aa63-1a499963502c','om','Oromo','Oromo',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('d17dc631-cdd4-4c87-bc90-1406b81e76e3','os','Ossetisch','Ossetian',5);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('60bb898b-4188-4745-b6a5-49058b835788','pa','Panjabi','Panjabi',5);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('556c685f-1410-4c82-8e40-2902a44f8c5b','pi','Pali','Pali',0);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('bd218ffb-a7b4-43a3-97e8-a9a469ad5b02','pl','Polnisch','Polish',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('cb305a0b-41bd-41cb-b59e-d3ab2b671c2d','pt','Portugiesisch','Portuguese',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('e58d2899-d645-449f-b40d-485d8d922920','ps','Paschtunisch','Pushto',5);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('b1dbe625-d934-474f-9a9f-643351344b40','qu','Quechua','Quechua',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('257cfd9e-0f56-4f4a-9504-fb7041629ba6','rm','Romanisch','Romansh',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('cc58171b-d076-4dec-999a-6d8aed9dfe4b','ro','Rum??nisch','Romanian',5);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('7b07b29a-7808-4ab0-83c3-9c798bac8c53','rn','Kirundi','Rundi',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('b645f1dd-ff42-41cb-8ed3-bddcc3f712a5','ru','Russisch','Russian',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('bc13077a-48bd-4e20-b521-2b074f7819f4','sg','Sango','Sango',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('e8877664-4507-4a70-b164-c00a5bb26930','sa','Sanskrit','Sanskrit',0);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('00d63d38-a402-4b41-a27a-e313cf71775a','si','Singhalesisch','Sinhala',5);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('1f6f134f-9007-4240-8ad7-f0b9578c3b4c','sk','Slowakisch','Slovak',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('3b4e6a9a-93a9-4348-8d16-c85519902aef','sl','Slowenisch','Slovenian',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('9f10b38e-7100-4a13-9b60-0f699967abf7','se','Nordsamisch','Northern Sami',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('27051f40-dc33-4b82-85c6-2b507e2a4ca3','sm','Samoanisch','Samoan',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('3a9b40bc-2fe7-4a7d-9237-d58481581194','sn','Shona','Shona',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('dbd26278-2dcb-4f04-8750-e7c3f919836e','sd','Sindhi','Sindhi',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('12fa976b-532f-46e8-a20e-398d6e62a84f','so','Somali','Somali',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('6622a936-6825-4079-bd24-95e8fc7d38b8','st','Sesoth','Sotho',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('ca4776db-db47-4138-bfcc-faaa285d8ded','sq','Albanisch','Albanian',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('27272fe9-0edf-47d5-be58-f9ef1b059ff5','sc','Sardisch','Sardinian',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('6d57ea18-d574-44bf-b091-986b1fc86af1','sr','Serbisch','Serbian',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('e08ef3c2-00ad-4501-8821-cf2e3a0aa9a1','ss','Siswati','Swati',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('0844e6db-8117-4624-bd25-c46a4f9208b0','su','Sundanesisch','Sundanese',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('d8db3cd8-56ea-4cdb-bf11-230815983c8a','sw','Swahili','Swahili',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('1d2fdcb2-6bde-441d-bfcc-db03c39c59d0','sv','Schwedisch','Swedish',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('45428ed0-1417-4387-9c00-60a1f787ef1e','ty','Tahitianisch','Tahitian',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('bba03f4c-600e-403d-849b-8b453c45c368','ta','Tamil','Tamil',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('c01b6cd9-f68b-4e8e-a29c-bb1cae562e40','tt','Tatarisch','Tatar',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('63115d90-8775-4e0d-9f6d-02aeb63dc137','te','Telugu','Telugu',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('a051d779-c94d-43ca-8756-117f5469cfac','tg','Tadschikisch','Tajik',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('e5cc1901-7050-4adb-a5fb-f0dd414c6415','tl','Tagalog','Tagalog',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('262340b0-f937-4098-bd54-8a434cbd00f1','th','Thai','Thai',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('51b6adef-7d61-43b7-b7e1-25afc69538da','ti','Tigrinya','Tigrinya',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('825dffab-dab1-4864-b022-1eb4fa10f929','to','Tongaisch','Tonga',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('b92736fc-f3d6-463f-8dbb-4bedca7a9578','tn','Setswana','Tswana',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('9bfed63d-7513-4b8b-85e2-0aaebee884f6','ts','Xitsonga','Tsonga',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('f60890b9-f9e6-4185-97f2-0c69e7e3f31a','tk','Turkmenisch','Turkmen',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('a6dc1d24-1beb-4d5c-a695-c8d2a8358f83','tr','T??rkisch','Turkish',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('991df781-dc34-42c9-9c02-c0026bc07399','tw','Twi','Twi',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('efa0cca4-26f8-4713-9f8a-a56b383a8b13','ug','Uigurisch','Uighur',5);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('6dba184a-90e2-4f2a-83f5-4873fcd9a669','uk','Ukrainisch','Ukrainian',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('61769fb0-1319-47ac-b41a-2f58376c29d3','ur','Urdu','Urdu',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('a9b5abdd-479a-4751-ade3-999aba84b3ec','uz','Usbekisch','Uzbek',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('1f9f2547-eccc-4cc0-a43c-fae34ed2f9f4','ve','Tshivenda','Venda',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('5b95c71d-cd84-4377-bafd-3b24fc55b201','vi','Vietnamesisch','Vietnamese',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('80f2cbc9-193a-4a61-b561-a45b77f752da','vo','Volap??k','Volap??k',2);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('7239ad74-53ed-488e-b822-0bf958583db3','wa','Wallonisch','Walloon',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('b430179c-d485-4c7e-a724-c9b454015dd0','wo','Wolof','Wolof',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('a1eedd5e-699f-46f9-94a9-f7d0fe46bbed','xh','isiXhosa','Xhosa',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('bb5e51c6-23a3-4b54-87d6-9593989ba719','yi','Jiddisch','Yiddish',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('a06d67b2-ed28-424c-b698-bd803c4a6959','yo','Yoruba','Yoruba',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('0f8de131-b97b-4476-b4f9-c939a7cfabae','za','Zhuang','Zhuang',5);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('d3bf8abc-8521-4df4-ac90-2e99f473341e','zh','Chinesisch','Chinese',1);
INSERT INTO language(id,iso_identifier2,german_name,english_name,type) VALUES ('d9095f79-0921-449d-a4ff-1164e1259404','zu','isiZulu','Zulu',1);