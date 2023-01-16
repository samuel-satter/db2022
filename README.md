# db2022

## ER Diagram
```mermaid
  erDiagram
    Student ||--o{ Phone : has
    Student }|--o| Grade : has
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
        string number
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
    StudentHobby {
        int StudentId
        int HobbyId
    }
    
    Grade {
        int GradeId
        string name
    }
    

```

## Commands that are required to run the script
* curl -L  https://gist.github.com/miwashiab/d891a64c7f73f4c8c3b5cfee2b3de776/raw/denormalized-data.csv -o denormalized-data.csv
* //denormalized-data.csv goes in// var/lib/mysql-files/denormaized-data.csv 
* curl -L https://raw.githubusercontent.com/samuel-satter/db2022/main/normalisering.sql -o normalisering.sql
* docker start iths-mysql
* docker exec -i iths-mysql mysql -uiths -piths < normalisering.sql
* ./gradlew cleanTest
* ./gradlew test

