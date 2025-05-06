package solutis.elasticode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import solutis.elasticode.dtos.TaskDto;
import solutis.elasticode.entities.Task;
import solutis.elasticode.enums.Status;
import solutis.elasticode.services.TaskService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private TaskService service;

    @Test
    @Order(1)
    void createTask_validTask_returnsCreatedTask() throws Exception {
        TaskDto dto = new TaskDto("Task Teste", "Isso é um teste", Status.EM_ANDAMENTO);

        mvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Task Teste"))
                .andExpect(jsonPath("$.description").value("Isso é um teste"))
                .andExpect(jsonPath("$.status").value("EM_ANDAMENTO"));
    }

    @Test
    @Order(2)
    void getAllTasks_returnTaskList() throws Exception {
        TaskDto dto = new TaskDto("Task Teste", "Isso é um teste", Status.EM_ANDAMENTO);
        service.criarTask(dto);

        mvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Task Teste"));
    }

    @Test
    @Order(3)
    void getTaskById_validId_returnsTask() throws Exception {
        TaskDto dto = new TaskDto("Task Teste", "Isso é um teste", Status.EM_ANDAMENTO);
        service.criarTask(dto);

        mvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Task Teste"));
    }

    @Test
    @Order(4)
    void updateTaskById_validRequestAndId_updatesAndReturnsUpdatedTask() throws Exception {
        TaskDto dto = new TaskDto("Task Teste", "Isso é um teste", Status.EM_ANDAMENTO);
        Task taskCriada = service.criarTask(dto);

        TaskDto dtoAtt = new TaskDto("Teste de update", "Isso é um teste de update", Status.CONCLUIDA);
        service.atualizarTask(dtoAtt, taskCriada.getId());

        mvc.perform(put("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dtoAtt)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Teste de update"))
                .andExpect(jsonPath("$.description").value("Isso é um teste de update"))
                .andExpect(jsonPath("$.status").value("CONCLUIDA"));
    }

    @Test
    @Order(5)
    void deleteTaskById_ValidId_ReturnsNoContent() throws Exception {
        TaskDto dto = new TaskDto("Task Teste", "Isso é um teste", Status.EM_ANDAMENTO);
        service.criarTask(dto);

        mvc.perform(delete("/tasks/1"))
                .andExpect(status().isNoContent());
    }
}
