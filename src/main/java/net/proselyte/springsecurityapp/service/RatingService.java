package net.proselyte.springsecurityapp.service;

public interface RatingService {
    void addMark(Long user_id, Long instr_id, double mark);
}
