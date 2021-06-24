-- add new languages that are needed for states
INSERT INTO language (code, name, native_name, "code_2t", "code_2b", code_3)
    VALUES ('es', 'Spanish, Castilian', 'Español', 'spa', 'spa', 'spa');

INSERT INTO language (code, name, native_name, "code_2t", "code_2b", code_3)
    VALUES ('ca', 'Catalan, Valencian', 'català, valencià', 'cat', 'cat', 'cat');


-- add states
INSERT INTO state (country_id, language_id, code, name, local_name, subdivision_category)
VALUES ((select c.id from country as c where c.code = UPPER('us')),
        (select l.id from language l where l.code = 'en'),
        'US-AL', 'Alabama', null, 'state');

INSERT INTO state (country_id, language_id, code, name, local_name, subdivision_category)
VALUES ((select c.id from country as c where c.code = UPPER('us')),
        (select l.id from language l where l.code = 'en'),
        'US-CA', 'California', null, 'state');

INSERT INTO state (country_id, language_id, code, name, local_name, subdivision_category)
VALUES ((select c.id from country as c where c.code = UPPER('us')),
        (select l.id from language l where l.code = 'en'),
        'US-DC', 'District of Columbia', null, 'district');

INSERT INTO state (country_id, language_id, code, name, local_name, subdivision_category)
VALUES ((select c.id from country as c where c.code = UPPER('es')),
        (select l.id from language l where l.code = 'es'),
        'ES-AN*', 'Andalucía', null, 'auto_comm');

INSERT INTO state (country_id, language_id, code, name, local_name, subdivision_category)
VALUES ((select c.id from country as c where c.code = UPPER('es')),
        (select l.id from language l where l.code = 'ca'),
        'ES-CT*', 'Catalunya [Cataluña]', null, 'auto_comm');

INSERT INTO state (country_id, language_id, code, name, local_name, subdivision_category)
VALUES ((select c.id from country as c where c.code = UPPER('ru')),
        (select l.id from language l where l.code = 'ru'),
        'RU-KHM*', 'Hanty-Mansijskij avtonomnyj okrug', 'Jugra', 'auto_dist');
