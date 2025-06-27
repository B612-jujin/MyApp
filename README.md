flowchart TD
    %% 시작
    Start([사용자])

    %% 메인 네비 메뉴
    Start -->|클릭 Home| Home[Home (Goals Dashboard)]
    Start -->|클릭 Study| Study[Study Page]
    Start -->|클릭 Community| Community[Community Chat]
    Start -->|클릭 Music| Music[Music Bar]
    Start -->|클릭 External▾| External[External Menu]
    Start -->|클릭 Profile▾| Profile[Profile Menu]

    %% Home 서브
    Home --> Goals[Goals Dashboard]
    Goals --> NewGoal[New Goal]
    Goals --> EditGoal[Edit/Delete Goal]

    %% Study 서브
    Study --> Timer[Simple Timer]
    Study --> Calendar[Monthly Calendar]

    %% Community 서브
    Community --> ChatRoom[Chat Room]

    %% Music 서브
    Music --> FilePlayer[File Player]
    Music --> URLPlayer[YouTube URL Player]

    %% External 서브
    External --> AILink[AI (새 탭)]
    External --> GitHubLink[GitHub (새 탭)]

    %% Profile 서브
    Profile --> ProfilePage[Profile Page]
    Profile --> SignOut[Sign Out]
