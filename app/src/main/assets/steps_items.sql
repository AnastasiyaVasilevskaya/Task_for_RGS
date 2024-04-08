BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "steps_items" (
	"id"	INTEGER NOT NULL UNIQUE,
	"name"	TEXT NOT NULL,
	"enabled"	INTEGER NOT NULL DEFAULT 1,
	PRIMARY KEY("id" AUTOINCREMENT)
);
INSERT INTO "steps_items" VALUES (1,'1.Получить от приемщика заказов извещение об инциденте, аварии.',1);
INSERT INTO "steps_items" VALUES (2,'2.Инструктаж  бригады, доведение информации по объекту и характеру.',1);
INSERT INTO "steps_items" VALUES (3,'3.Установка автомобиля, обеспечение освещения, и проезда спецтранспорта.',1);
COMMIT;
