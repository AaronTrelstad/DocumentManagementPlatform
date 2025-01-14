import {
  Box,
  Card,
  CardActions,
  CardContent,
  CardHeader,
  CardMedia,
  IconButton,
  Typography,
} from "@mui/material";
import FavoriteIcon from "@mui/icons-material/Favorite";
import InsightsIcon from "@mui/icons-material/Insights";
import AssistantIcon from "@mui/icons-material/Assistant";
import AddCommentIcon from "@mui/icons-material/AddComment";
import DeleteIcon from "@mui/icons-material/Delete";
import RemoveRedEyeIcon from "@mui/icons-material/RemoveRedEye";
import ModeEditIcon from "@mui/icons-material/ModeEdit";
import { Document } from "../types/document";
import axios from "axios";
import { useState } from "react";
import { EditDocumentDialog } from "./editDocument";
import { CommentDocument } from "./commentDocument";

export function DocumentCard({
  document,
  onDocumentChange,
}: {
  document: Document;
  onDocumentChange: () => void;
}) {
  const [editDialogOpen, setEditDialogOpen] = useState(false);
  const [commentDialogOpen, setCommentDialogOpen] = useState(false);

  const handleDelete = async () => {
    try {
      await axios.delete(`http://localhost:8080/api/documents/${document.id}`);
      onDocumentChange();
    } catch (error) {
      console.error("Error deleting document:", error);
    }
  };

  const openPDFViewer = () => {
    const binary = atob(document.fileBase64);
    const length = binary.length;
    const buffer = new ArrayBuffer(length);
    const view = new Uint8Array(buffer);

    for (let i = 0; i < length; i++) {
      view[i] = binary.charCodeAt(i);
    }

    const blob = new Blob([buffer], { type: "application/pdf" });
    const url = URL.createObjectURL(blob);
    window.open(url, "_blank");
  };

  const isPDF = (fileBase64: string) => {
    return fileBase64.startsWith("JVBERi0xL");
  };

  const handleLike = async () => {
    try {
      await axios.patch(`http://localhost:8080/api/documents/${document.id}/like`)
      onDocumentChange();
    } catch(error) {
      console.error(error)
    }
  }

  return (
    <>
      <Card sx={{ width: 400, height: 400, display: "flex", flexDirection: "column" }}>
        <CardHeader
          title={document.name}
          action={
            <IconButton aria-label="edit" onClick={() => setEditDialogOpen(true)}>
              <ModeEditIcon />
            </IconButton>
          }
        />

        {isPDF(document.fileBase64) ? (
          <CardMedia
            component="iframe"
            height="140"
            src={`data:application/pdf;base64,${document.fileBase64}`}
            title={`${document.name} PDF`}
          />
        ) : (
          <CardMedia
            component="img"
            height="140"
            image={`data:image/jpeg;base64,${document.fileBase64}`}
            alt={`${document.name} image`}
          />
        )}

        <CardContent>
          <Typography variant="body2" color="text.secondary">
            {document.description}
          </Typography>
        </CardContent>

        <CardActions>
          <IconButton
            aria-label="like"
            onClick={handleLike}
            sx={{
              "&:hover": {
                color: "red",
              },
            }}
          >
            <FavoriteIcon />
            <Typography>{document.likes}</Typography>
          </IconButton>
          <IconButton
            aria-label="comment"
            onClick={() => setCommentDialogOpen(true)}
            sx={{
              "&:hover": {
                color: "lightblue",
              },
            }}
          >
            <AddCommentIcon />
          </IconButton>
          <IconButton
            aria-label="analytics"
            sx={{
              "&:hover": {
                color: "green",
              },
            }}
          >
            <InsightsIcon />
          </IconButton>
          <IconButton
            aria-label="assistant"
            sx={{
              "&:hover": {
                color: "purple",
              },
            }}
          >
            <AssistantIcon />
          </IconButton>
          <IconButton
            aria-label="delete"
            onClick={handleDelete}
            sx={{
              "&:hover": {
                color: "black",
              },
            }}
          >
            <DeleteIcon />
          </IconButton>
          <IconButton
            aria-label="view"
            onClick={openPDFViewer}
            sx={{
              "&:hover": {
                color: "blue",
              },
            }}
          >
            <RemoveRedEyeIcon />
          </IconButton>
        </CardActions>

        <Box
          sx={{
            maxHeight: "100px",
            overflowY: "auto",
            padding: "8px",
            borderTop: "1px solid #ddd",
            margin: "0 16px",
          }}
        >
          {document.comments.length > 0 ? (
            document.comments.map((comment, index) => (
              <Typography key={index} variant="body2" sx={{ marginBottom: "4px" }}>
                <strong>{comment.commenterId || "Anonymous"}:</strong> {comment.message}
              </Typography>
            ))
          ) : (
            <Typography variant="body2" color="text.secondary">
              No comments yet.
            </Typography>
          )}
        </Box>
      </Card>
      <EditDocumentDialog
        open={editDialogOpen}
        onClose={() => setEditDialogOpen(false)}
        document={document}
        onDocumentUpdate={onDocumentChange}
      />
      <CommentDocument
        open={commentDialogOpen}
        onClose={() => setCommentDialogOpen(false)}
        document={document}
        onDocumentUpdate={onDocumentChange}
      />
    </>
  );
}
