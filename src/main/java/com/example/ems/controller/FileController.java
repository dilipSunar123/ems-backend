package com.example.ems.controller;

import com.example.ems.entity.EmployeeEntity;
import com.example.ems.entity.FileEntity;
import com.example.ems.entity.FileTypeEntity;
import com.example.ems.repository.EmployeeRepo;
import com.example.ems.repository.FileRepository;
import com.example.ems.repository.FileTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class FileController {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    private FileTypeRepository fileTypeRepository;

    private final JavaMailSender javaMailSender;

    public FileController(FileTypeRepository fileTypeRepository, JavaMailSender javaMailSender) {
        this.fileTypeRepository = fileTypeRepository;
        this.javaMailSender = javaMailSender;
    }


    @PostMapping("/uploadFile/{empId}/{fileName}/{fileTypeId}")
    private ResponseEntity<?> uploadFile (@PathVariable int empId, @PathVariable String fileName,@PathVariable int fileTypeId, @RequestParam("file")MultipartFile file) {

        // check if employee exists
        Optional<EmployeeEntity> employeeEntity = employeeRepo.findById(empId);

        if (employeeEntity.isPresent()) {
            try {
                // get the byte array from uploaded file
                byte[] uploadedFile = file.getBytes();

                // upload the file
                FileEntity fileEntity = new FileEntity();

                fileEntity.setFileName(fileName);
                fileEntity.setData(uploadedFile);
                EmployeeEntity employee = employeeRepo.getReferenceById(empId);
                fileEntity.setEmployeeEntity(employee);
                FileTypeEntity fileType = fileTypeRepository.getReferenceById(fileTypeId);
                fileEntity.setFileTypeEntity(fileType);


                fileRepository.save(fileEntity);

                // mail manager if new file added
                SimpleMailMessage message = new SimpleMailMessage();

                int managerId = employee.getReportingManagerId();
                String emailOfManager = employeeRepo.getReferenceById(managerId).getEmail();

                message.setTo(emailOfManager);
                message.setSubject("New Document Uploaded!");
                message.setText("Hi, \n\n" + employee.getEmp_name() + " has uploaded a new document. \n\n" +
                        "Below are the details - \n\n" +
                        "Employee name - " + employee.getEmp_name() +
                        "\nEmployee department - " + employee.getDepartmentEntity().getDept_name() +
                        "\nDepartment Location - " + employee.getDepartmentEntity().getLocation() +
                        "\nDocument - " + fileName + "\n\nClick on the link below to approve\n");

                javaMailSender.send(message);

                return ResponseEntity.ok("File uploaded successfully");

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("done");
            }
        }

        return ResponseEntity.ok("Something went wrong!");

    }

    @GetMapping("/viewFile/{empId}/{fileTypeId}")
    private ResponseEntity<?> viewFile (@PathVariable int empId, @PathVariable int fileTypeId) {

        List<FileEntity> fileEntityList = fileRepository.findByEmployeeEntityEmpIdAndFileTypeEntityFileTypeId(empId, fileTypeId);

        HashMap<Integer, String> files = new HashMap<>();

        if (!fileEntityList.isEmpty()) {

            for (FileEntity fileEntity: fileEntityList) {
                files.put(fileEntity.getId(), fileEntity.getFileName());
            }

            return ResponseEntity.ok().body(files);

        }
        return ResponseEntity.internalServerError().body("Could not fetch files");
    }

    @GetMapping("/findStatusById/{fileId}")
    private String findStatusById(@PathVariable int fileId) {

        FileEntity entity = fileRepository.getReferenceById(fileId);

        return entity.getStatus();
    }

    @GetMapping("/viewFiles/{empId}")
    private ResponseEntity<?> viewFiles(@PathVariable int empId) {

        List<FileEntity> fileEntityList = fileRepository.findByEmployeeEntityEmpId(empId);

        HashMap<Integer, String> files = new HashMap<>();

        if (!fileEntityList.isEmpty()) {

            for (FileEntity fileEntity: fileEntityList) {
                files.put(fileEntity.getId(), fileEntity.getFileName());
            }

            return ResponseEntity.ok().body(files);

        }
        return ResponseEntity.internalServerError().body("Something went wrong!");
    }


    @GetMapping("/viewFiles")
    private ResponseEntity<?> viewFiles() {
        List<FileEntity> fileEntityList = fileRepository.findAll();

        HashMap<Integer, String> files = new HashMap<>();

        if (!fileEntityList.isEmpty()) {
            for (FileEntity fileEntity: fileEntityList) {
                files.put(fileEntity.getId(), fileEntity.getFileName());
            }

            return ResponseEntity.ok().body(files);

        }
        return ResponseEntity.internalServerError().body("Something went wrong!");
    }



    @GetMapping("/viewFileById/{fileId}")
    private ResponseEntity<byte[]> viewFileById(@PathVariable int fileId) {

        Optional<FileEntity>fileEntityOptional = fileRepository.findById(fileId);

        if (fileEntityOptional.isPresent()) {
            FileEntity entity = fileEntityOptional.get();

            byte[] file = entity.getData();

            if (file != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);

                // Return the image data as a byte array
                return ResponseEntity.ok().headers(headers).body(file);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.notFound().build();
    }



//    @GetMapping("/viewFile/{empId}/{fileTypeId}")
//    private ResponseEntity<byte[]> viewFile(@PathVariable int empId, @PathVariable int fileTypeId) {
//        Optional<EmployeeEntity> optionalEmployee = employeeRepo.findById(empId);

//        byte[] file = new byte[1];
//        if (optionalEmployee.isPresent()) {
//            EmployeeEntity employee = optionalEmployee.get();
//
//            file = employee.getProfilePicture();
//        }
//
//        return ResponseEntity.ok(file);

//        FileEntity fileEntity = (FileEntity) fileRepository.findByEmployeeEntityEmpIdAndFileTypeEntityFileTypeId(empId, fileTypeId);
//
//
//
//    }



//    @GetMapping("/findFileByCategory/{empId}/{fileTypeId}")
//    public List<UploadedFile> findFileByCategory(@PathVariable int empId, @PathVariable int fileTypeId) {
//        return uploadedFileRepository.findByEmployeeEntityEmpIdAndFileTypeEntityFileTypeId(empId, fileTypeId);
//    }

//    @DeleteMapping("/removeFile/{fileId}")
//    private ResponseEntity<?> removeFile(@PathVariable int fileId) {
//        uploadedFileRepository.deleteById(fileId);
//
//        return ResponseEntity.ok("file removed successfully!");
//    }

    // delete file
    @DeleteMapping("/deleteFile/{fileId}")
    private ResponseEntity<?> deleteFile (@PathVariable int fileId) {
        fileRepository.delete(fileRepository.getReferenceById(fileId));

        return ResponseEntity.ok("File removed");
    }


    @PutMapping("/changeFileStatus/{fileId}/{status}")
    private ResponseEntity<?> changeStatus(@PathVariable int fileId, @PathVariable String status) {

        FileEntity entity = fileRepository.getReferenceById(fileId);

        entity.setStatus(status);

        fileRepository.save(entity);

        return ResponseEntity.ok("File status changed!");
    }

}
