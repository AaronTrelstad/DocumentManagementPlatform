import {
    Box,
    Button,
    Dialog,
    DialogContent,
    DialogContentText,
    DialogTitle,
    TextField,
    Input,
    DialogActions,
  } from "@mui/material";
  import AddIcon from "@mui/icons-material/Add";
  import { useState } from "react";
  import axios from "axios";
  
  export function AddDocument({ onDocumentAdded }: { onDocumentAdded: () => void }) {
    const [dialogOpen, setDialogOpen] = useState(false);
    const [formData, setFormData] = useState({
      name: "",
      description: "",
      file: null as File | null,
      submitterId: "12345", 
    });
  
    const handleDialogOpen = () => {
      setDialogOpen(true);
    };
  
    const handleDialogClose = () => {
      setDialogOpen(false);
    };
  
    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
      const { name, value } = e.target;
      setFormData((prev) => ({ ...prev, [name]: value }));
    };
  
    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
      if (e.target.files && e.target.files.length > 0) {
        setFormData((prev) => ({ ...prev, file: e.target.files![0] }));
      }
    };
  
    const uploadDocument = async () => {
      if (!formData.name || !formData.description || !formData.file) {
        alert("Please fill all fields and upload a file.");
        return;
      }
  
      const data = new FormData();
      data.append("name", formData.name);
      data.append("description", formData.description);
      data.append("submitterId", formData.submitterId);
      data.append("file", formData.file);
      data.append("folderId", "1")

      console.log(data)
  
      try {
        const response = await axios.post(
          "http://localhost:8080/api/documents/",
          data,
          {
            headers: {
              "Content-Type": "multipart/form-data",
            },
          }
        );
  
        onDocumentAdded();
        console.log("Document uploaded successfully:", response.data);
        handleDialogClose();
      } catch (error) {
        console.error("Error uploading document:", error);
        alert("Failed to upload the document. Please try again.");
      }
    };
  
    return (
      <Box>
        <Button
          variant="outlined"
          startIcon={<AddIcon />}
          onClick={handleDialogOpen}
          sx={{ margin: 6 }}
        >
          Add Document
        </Button>
  
        <Dialog
          open={dialogOpen}
          onClose={handleDialogClose}
          fullWidth
          maxWidth="sm"
        >
          <DialogTitle>Add Document</DialogTitle>
          <DialogContent>
            <DialogContentText>
              Fill out the form below to upload a document.
            </DialogContentText>
            <TextField
              autoFocus
              required
              margin="dense"
              id="name"
              name="name"
              label="Name"
              value={formData.name}
              onChange={handleInputChange}
              fullWidth
              variant="standard"
            />
            <TextField
              required
              margin="dense"
              id="description"
              name="description"
              label="Description"
              value={formData.description}
              onChange={handleInputChange}
              fullWidth
              variant="standard"
            />
            <Input
              type="file"
              onChange={handleFileChange}
              required
              sx={{ marginTop: 2 }}
            />
          </DialogContent>
          <DialogActions>
            <Button onClick={handleDialogClose}>Cancel</Button>
            <Button onClick={uploadDocument}>Save</Button>
          </DialogActions>
        </Dialog>
      </Box>
    );
  }
  