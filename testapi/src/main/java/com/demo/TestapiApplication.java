package com.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@RequestMapping("api")
public class TestapiApplication {

	private final MemoRepository memoRepository;
	
	public TestapiApplication(MemoRepository memoRepository){this.memoRepository = memoRepository;}
	
	public static void main(String[] args) {
		SpringApplication.run(TestapiApplication.class, args);
	}
	
	@GetMapping
	public List<Memo> getMemos() {
        return memoRepository.findAll();
    }
	
	@GetMapping("/{id}")
	public Optional<Memo> getMemo(@PathVariable("id") int id) {
        return memoRepository.findById(id);
    }
		
	
	record NewMemo(int id,String memo){}

    @PostMapping
    public Memo createMemo(@RequestBody Memo memo) {
        return memoRepository.save(memo);
    }

    @PutMapping("/{id}")
    public Memo updateMemo(@PathVariable int id, @RequestBody Memo newMemo) {
        return memoRepository.findById(id).map(memo -> {
            memo.setMemo(newMemo.getMemo());
            return memoRepository.save(memo);
        }).orElseGet(() -> {
            newMemo.setId(id);
            return memoRepository.save(newMemo);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteMemo(@PathVariable int id) {
        memoRepository.deleteById(id);
    }


}
