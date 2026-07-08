package bamv.training.microposts.service.impl;

import bamv.training.microposts.dao.MSequenceDao;
import bamv.training.microposts.entity.MSequence;
import bamv.training.microposts.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SequenceServiceImpl implements SequenceService {
    @Autowired
    private MSequenceDao mSequenceDao;

    @Override
    @Transactional
    public String issueSequence(String idName) {
        MSequence mSequence = mSequenceDao.findSequence(idName);
        mSequenceDao.incrementSequenceCurrentNumber(mSequence);
        return String.format("%s%08d", mSequence.getPrefix(), mSequence.getCurrentNumber() + 1);
    }
}
