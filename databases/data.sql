USE `BaseDeDatos`;


INSERT
INTO User
VALUES
('','Javier', 'Rafael', 'Sánchez'),
('','Pepito', 'Rodriguez', 'Finito');

INSERT
INTO Message
VALUES
('',1,'mensaje de javier',1,1,'1-11-2012'),
('',2,'mensaje de pepito',1,1,'1-11-2012');



INSERT
INTO Tablon
VALUES
('','Tablon global',1,'espacio');


INSERT 
INTO TablonTargetUser
VALUES
('',1,2);


INSERT 
INTO TablonMessage
VALUES
('',1,1);


INSERT 
INTO TablonUserModerates
VALUES
('',1,1);



INSERT 
INTO UserPermission
VALUES
('',1,4),
('',2,4);