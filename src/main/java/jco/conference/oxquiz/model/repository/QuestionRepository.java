package jco.conference.oxquiz.model.repository;

import jco.conference.oxquiz.model.Question;

/**
 * @author ykpark.planet@sk.com
 * @date 14. 2. 18.
 */
public interface QuestionRepository extends Repository<Question> {

    Question findOneByLastCreatedDateTime();

}
