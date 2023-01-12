# db2022

## H2 ER Diagram

erDiagram
	Student ||--o{ Phone : has
	Student ||--o{ StudentSchool : attends
	School ||--o{ StudentSchool : enrolls
	Student ||--o{ StudentHobby : has
	Hobby ||--o{ StudentHobby : involves

	Student {
		int StudentId
		string Name
		int GradeId
	}

	Phone {
		int PhoneId
		int StudentId
		tinyint IsHome
		tinyint IsJob
		tinyint IsMobile
		string Number
	}

	School {
		int SchoolId
		string name
		string City
	}

	StudentSchool {
		int StudentId
		int SchoolId
	}

	Hobby {
		int HobbyId
		string name
	}

	Grade {
		int GradeId
		string name
	}

