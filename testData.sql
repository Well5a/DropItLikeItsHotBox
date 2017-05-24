USE dropbox;

INSERT INTO dropbox.user (oId, username, passwd, email)
VALUES  (3, 'Julian', '12345', 'juscit06@hs-esslingen.de'),
		(2, 'David', '12345', 'dascit12@hs-esslingen.de'),
        (1, 'Marcel', '12345', 'maweit06@hs-esslingen.de');
        
INSERT INTO dropbox.groups (oId, groupsName)
VALUES (1, 'Examplegroups');
        
INSERT INTO dropbox.usergroups (oId, userId, groupsId)
VALUES	(1, 3, 1),
		(2, 2, 1);

INSERT INTO dropbox.file (oId, path, ownerId)
VALUES (1, 'C:\Users\User1\Documents\file.txt', 3);

INSERT INTO dropbox.filepermission (oId, allowRead, allowWrite, fileId, userId)
VALUES(1, 1, 1, 1, 3);