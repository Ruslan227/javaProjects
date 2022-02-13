package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Notice;
import ru.itmo.wp.form.NoticeCredentials;
import ru.itmo.wp.repository.NoticeRepository;

import java.util.List;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;


    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public List<Notice> findAll() {
        return noticeRepository.findAll();
    }

    public boolean isNoticeVacant(String content) {
        return noticeRepository.countByContent(content) == 0;
    }

    public void addNotice(NoticeCredentials noticeCredentials) {
        Notice notice = new Notice();
        notice.setContent(noticeCredentials.getContent());
        noticeRepository.save(notice);
    }
}
