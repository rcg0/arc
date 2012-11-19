USE `BaseDeDatos`;


INSERT
INTO User
VALUES
('','Javier', 'Rafael', 'Sánchez'),
('','Pepito', 'Rodriguez', 'Finito');



INSERT
INTO Tablon
VALUES
('','Tablon global',1,'espacio');

INSERT
INTO Message
VALUES
('',1,1,'mensaje de javier',1,1,NOW());



INSERT 
INTO TablonTargetUser
VALUES
('',1,2);



INSERT 
INTO TablonUserModerates
VALUES
('',1,1);



INSERT 
INTO UserPermission
VALUES
('',1,1,4),
('',2,1,4);
