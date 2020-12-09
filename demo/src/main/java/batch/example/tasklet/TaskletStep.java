package batch.example.tasklet;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
 
@Component
public class TaskletStep implements Tasklet {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		String path = "C:\\Temp";
	    deleteFolder(path);

        return RepeatStatus.FINISHED;
	}
	
	private void deleteFolder(String path) {

		File folder = new File(path);
	    try {
		if(folder.exists()){
                File[] folder_list = folder.listFiles(); //파일리스트 얻어오기
				
		for (int i = 0; i < folder_list.length; i++) {
		    if(folder_list[i].isFile()) {
			folder_list[i].delete();
			System.out.println("파일이 삭제되었습니다.");
		    }else {
			deleteFolder(folder_list[i].getPath()); //재귀함수호출
			System.out.println("폴더가 삭제되었습니다.");
		    }
		    folder_list[i].delete();
		 }
		 folder.delete(); //폴더 삭제
	       }
	   } catch (Exception e) {
		e.getStackTrace();
	   }
		
}
}
