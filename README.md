### Git Convention
#### 1. Branch 전략
- 브랜치명 : ``` feat/{이슈번호}/{작업설명}```
- 일회성 브랜치 -> main 머지 시 일반 merge 사용 

#### 2. Commit convention
- ```feat:, fix:, docs:, refactor:, chore:``` 등으로 시작
- commit message 본문에 작업 상세 내용 기재

- https://velog.io/@mw310/GitGithub-Commit-Convention%EC%9D%B4%EB%9E%80 참고

#### 3. PR/ISSUE convention
#### 📌 이슈 규칙

- **Issue 등록 후** 작업 시작

- Issue 제목 형식:
```
  [FEAT]: 설명  
  [FIX]: 설명  
  [CHORE]: 설명
```
- ```Assignee``` 지정
- ```Label``` 설정


#### 🔀 Pull Request 규칙
- PR 제목 형식:
```
[FEAT] : 기능 설명
[FIX] : 버그 설명
[CHORE] : 기타 설명
```
- ```Label``` 설정
- 본문에 이슈 번호 연결 ```(close #1)```

- ```Merge``` 시 커밋 메시지에 이슈 번호 포함

   - ex) ```feat: Google OAuth 구현 (#17)```

- PR 본문에 작업 내용 & 통합 테스트 스크린샷 첨부

#### 4. code review
- 최소 2명에게 코드 리뷰 승인 받은 뒤에 main에 merge 가능 (매주 랜덤 지정)
- 본인 PR 직접 merge
