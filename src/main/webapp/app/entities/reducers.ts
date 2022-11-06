import template from 'app/entities/template/template.reducer';
import categories from 'app/entities/categories/categories.reducer';
import templateCategories from 'app/entities/template-categories/template-categories.reducer';
import templateFacilitators from 'app/entities/template-facilitators/template-facilitators.reducer';
import questions from 'app/entities/questions/questions.reducer';
import exam from 'app/entities/exam/exam.reducer';
import examQuestions from 'app/entities/exam-questions/exam-questions.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  template,
  categories,
  templateCategories,
  templateFacilitators,
  questions,
  exam,
  examQuestions,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
