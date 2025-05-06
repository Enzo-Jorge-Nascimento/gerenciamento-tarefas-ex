package solutis.elasticode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import solutis.elasticode.dtos.TaskDto;
import solutis.elasticode.enums.Status;
import solutis.elasticode.services.TaskService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper mapper;
    private final TaskService service;

    public TaskControllerTest(MockMvc mvc, ObjectMapper mapper, TaskService service) {
        this.mvc = mvc;
        this.mapper = mapper;
        this.service = service;
    }

    @Test
    void createTask_validTask_returnsCreatedTask() throws Exception {
        TaskDto dto = new TaskDto("Task Teste", "Isso é um teste", Status.EM_ANDAMENTO);

        mvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Task Teste"))
                .andExpect(jsonPath("$.description").value("Isso é um teste"))
                .andExpect(jsonPath("$.status").value(Status.EM_ANDAMENTO));
    }

    @Test
    void getAllTasks_returnTaskList() throws Exception {
        TaskDto dto = new TaskDto("Task Teste", "Isso é um teste", Status.EM_ANDAMENTO);
        service.criarTask(dto);

        mvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Task Teste"));
    }

    @Test
    void getTaskById_validId_returnsTask() throws Exception {
        mvc.perform(get("/tasks/1L"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Task Teste"));
    }

    @Test
    void updateTaskById_validRequestAndId_updatesAndReturnsUpdatedTask() throws Exception {
        TaskDto dto = new TaskDto("Teste de update", "Isso é um teste de update", Status.CONCLUIDA);

        mvc.perform(put("/tasks/1L")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Teste de update"))
                .andExpect(jsonPath("$.description").value("Isso é um teste de update"))
                .andExpect(jsonPath("$.status").value(Status.CONCLUIDA));
    }

    @Test
    void deleteTaskById_ValidId_ReturnsNoContent() throws Exception {
        mvc.perform(delete("/tasks/1L"))
                .andExpect(status().isNoContent());
    }
}
