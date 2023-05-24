INSERT INTO RECIPE (id, name, description, nutritional_score, preparation_time, author_id) VALUES ('06cded1b-7658-40c8-a212-1f7c297e7f90', 'Pâtes au chorizo', 'r_desc', 4.0, 12, '0692c72f-24e5-45c9-95ab-72feb8419fc6');

--INSERT INTO STEP (recipe_id, ingredient_id, step_number, description) VALUES ('06cded1b-7658-40c8-a212-1f7c297e7f90', NULL, 1, 'Mettre à chauffer de l\'eau');
INSERT INTO STEP (recipe_id, ingredient_id, step_number, description) VALUES ('06cded1b-7658-40c8-a212-1f7c297e7f90', 'bb159316-75cb-4c1f-86a7-8592293ec44b', 2, 'Découper le chozio en fine tranche');
INSERT INTO STEP (recipe_id, ingredient_id, step_number, description) VALUES ('06cded1b-7658-40c8-a212-1f7c297e7f90', '4109b605-6820-4b42-b3c8-923ec40a1d54', 3, 'Découper les poivrons en lamelle puis les couper en deux');
--INSERT INTO STEP (recipe_id, ingredient_id, step_number, description) VALUES ('06cded1b-7658-40c8-a212-1f7c297e7f90', '', 4, 'Cuire le chorizo dans une poile sans huile, une fois chauffés, ajouter les poivrons');
