import Grid from "@mui/material/Grid2";
import { useEffect, useState } from "react";
import { Box } from "@mui/material";
import { Document } from "../types/document";
import { AddDocument } from "../components/addDocument";
import { DocumentCard } from "../components/documentCard"; 
import axios from "axios";

export function Dashboard() {
  const [documents, setDocuments] = useState<Document[]>([]); 
  const [refreshKey, setRefreshKey] = useState(0); 

  const fetchDocuments = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/documents"); 
      setDocuments(response.data);
    } catch (error) {
      console.error("Error fetching documents:", error);
    }
  };

  useEffect(() => {
    fetchDocuments();
  }, [refreshKey]);

  const handleDocumentChange = () => {
    setRefreshKey((prev) => prev + 1);
  };

  return (
    <Box sx={{ padding: 2 }}>
      <AddDocument onDocumentAdded={handleDocumentChange} />

      <Grid
        container
        spacing={{ xs: 2, md: 4 }}
        columns={{ xs: 4, sm: 8, md: 12 }} 
      >
        {documents && documents.map((document) => (
          <Grid key={document.id}>
            <DocumentCard document={document} onDocumentChange={handleDocumentChange} />
          </Grid>
        ))}
      </Grid>
    </Box>
  );
}
