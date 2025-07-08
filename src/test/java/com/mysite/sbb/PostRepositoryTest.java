package com.mysite.sbb;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
class PostRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @Test
    @DisplayName("textFindAll")
    void t1() {
        List<Question> all = questionRepository.findAll();
        assertEquals(all.size(), 2);

        Question q = all.get(0);
        assertEquals(q.getSubject(),"sbb가 무엇인가요?");

    }

    @Test
    @DisplayName("findById")
    void t2() {
        Optional<Question> op = questionRepository.findById(1);

        if (op.isPresent()) {
            Question q = op.get();
            assertEquals(q.getSubject(),"sbb가 무엇인가요?");
        }
    }

    @Test
    @DisplayName("findBySubject")
    void t3() {
        Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
        assertEquals(q.getId(), 1);
    }

    @Test
    @DisplayName("findBySubjectAndContent")
    void t4() {
        Question q = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?" , "sbb에 대해서 알고 싶습니다.");

        assertEquals(q.getId() , 1);
    }

    @Test
    @DisplayName("findBySubjectLike")
    void t5() {
        List<Question> qList = questionRepository.findBySubjectLike("sbb%"); //sbb로 시작하는 문자열
        // %sbb = sbb로 끝나는
        // %sbb% = sbb가 포함된
    }

    @Test
    @DisplayName("modify")
    void t6() {
        Optional<Question> oq = questionRepository.findById(1);
        assertTrue(oq.isPresent());

        Question q = oq.get();
        q.setSubject("수정된 제목");
        questionRepository.save(q);
    }

    @Test
    @DisplayName("delete")
    void t7() {
        assertEquals(2, this.questionRepository.count());
        Optional<Question> oq = this.questionRepository.findById(1);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        this.questionRepository.delete(q);
        assertEquals(1, this.questionRepository.count());

    }

    @Test
    @DisplayName("답변 데이터 저장")
    void t8() {
        Optional<Question> oq = this.questionRepository.findById(2);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        Answer a = new Answer();
        a.setContent("네 자동으로 생성됩니다.");
        a.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
        a.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a);
    }
    @Test
    @DisplayName("답변 데이터 조회")
    void t9() {
        Optional<Answer> oa = this.answerRepository.findById(1);
        assertTrue(oa.isPresent());
        Answer a = oa.get();
        assertEquals(2, a.getQuestion().getId());
    }









}
