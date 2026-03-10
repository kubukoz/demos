$version: "2.0"

namespace lsp.services

use jsonrpclib#jsonRpc
use lsp#CallHierarchyIncomingCallsOp
use lsp#CallHierarchyOutgoingCallsOp
use lsp#CancelRequest
use lsp#ClientRegisterCapabilityOp
use lsp#ClientUnregisterCapabilityOp
use lsp#CodeActionResolveOp
use lsp#CodeLensResolveOp
use lsp#CompletionItemResolveOp
use lsp#DocumentLinkResolveOp
use lsp#Exit
use lsp#Initialized
use lsp#InitializeOp
use lsp#InlayHintResolveOp
use lsp#LogTrace
use lsp#NotebookDocumentDidChange
use lsp#NotebookDocumentDidClose
use lsp#NotebookDocumentDidOpen
use lsp#NotebookDocumentDidSave
use lsp#Progress
use lsp#SetTrace
use lsp#ShutdownOp
use lsp#TelemetryEvent
use lsp#TextDocumentCodeActionOp
use lsp#TextDocumentCodeLensOp
use lsp#TextDocumentColorPresentationOp
use lsp#TextDocumentCompletionOp
use lsp#TextDocumentDeclarationOp
use lsp#TextDocumentDefinitionOp
use lsp#TextDocumentDiagnosticOp
use lsp#TextDocumentDidChange
use lsp#TextDocumentDidClose
use lsp#TextDocumentDidOpen
use lsp#TextDocumentDidSave
use lsp#TextDocumentDocumentColorOp
use lsp#TextDocumentDocumentHighlightOp
use lsp#TextDocumentDocumentLinkOp
use lsp#TextDocumentDocumentSymbolOp
use lsp#TextDocumentFoldingRangeOp
use lsp#TextDocumentFormattingOp
use lsp#TextDocumentHoverOp
use lsp#TextDocumentImplementationOp
use lsp#TextDocumentInlayHintOp
use lsp#TextDocumentInlineValueOp
use lsp#TextDocumentLinkedEditingRangeOp
use lsp#TextDocumentMonikerOp
use lsp#TextDocumentOnTypeFormattingOp
use lsp#TextDocumentPrepareCallHierarchyOp
use lsp#TextDocumentPrepareRenameOp
use lsp#TextDocumentPrepareTypeHierarchyOp
use lsp#TextDocumentPublishDiagnostics
use lsp#TextDocumentRangeFormattingOp
use lsp#TextDocumentReferencesOp
use lsp#TextDocumentRenameOp
use lsp#TextDocumentSelectionRangeOp
use lsp#TextDocumentSemanticTokensFullDeltaOp
use lsp#TextDocumentSemanticTokensFullOp
use lsp#TextDocumentSemanticTokensRangeOp
use lsp#TextDocumentSignatureHelpOp
use lsp#TextDocumentTypeDefinitionOp
use lsp#TextDocumentWillSave
use lsp#TextDocumentWillSaveWaitUntilOp
use lsp#TypeHierarchySubtypesOp
use lsp#TypeHierarchySupertypesOp
use lsp#WindowLogMessage
use lsp#WindowShowDocumentOp
use lsp#WindowShowMessage
use lsp#WindowShowMessageRequestOp
use lsp#WindowWorkDoneProgressCancel
use lsp#WindowWorkDoneProgressCreateOp
use lsp#WorkspaceApplyEditOp
use lsp#WorkspaceCodeLensRefreshOp
use lsp#WorkspaceConfigurationOp
use lsp#WorkspaceDiagnosticOp
use lsp#WorkspaceDiagnosticRefreshOp
use lsp#WorkspaceDidChangeConfiguration
use lsp#WorkspaceDidChangeWatchedFiles
use lsp#WorkspaceDidChangeWorkspaceFolders
use lsp#WorkspaceDidCreateFiles
use lsp#WorkspaceDidDeleteFiles
use lsp#WorkspaceDidRenameFiles
use lsp#WorkspaceExecuteCommandOp
use lsp#WorkspaceInlayHintRefreshOp
use lsp#WorkspaceInlineValueRefreshOp
use lsp#WorkspaceSemanticTokensRefreshOp
use lsp#WorkspaceSymbolOp
use lsp#WorkspaceSymbolResolveOp
use lsp#WorkspaceWillCreateFilesOp
use lsp#WorkspaceWillDeleteFilesOp
use lsp#WorkspaceWillRenameFilesOp
use lsp#WorkspaceWorkspaceFoldersOp

@jsonRpc
service LSPClient {
    operations: [
        CancelRequest
        ClientRegisterCapabilityOp
        ClientUnregisterCapabilityOp
        LogTrace
        Progress
        TelemetryEvent
        TextDocumentPublishDiagnostics
        WindowLogMessage
        WindowShowDocumentOp
        WindowShowMessage
        WindowShowMessageRequestOp
        WindowWorkDoneProgressCreateOp
        WorkspaceApplyEditOp
        WorkspaceCodeLensRefreshOp
        WorkspaceConfigurationOp
        WorkspaceDiagnosticRefreshOp
        WorkspaceInlayHintRefreshOp
        WorkspaceInlineValueRefreshOp
        WorkspaceSemanticTokensRefreshOp
        WorkspaceWorkspaceFoldersOp
    ]
}

@jsonRpc
service LSPServer {
    operations: [
        CallHierarchyIncomingCallsOp
        CallHierarchyOutgoingCallsOp
        CancelRequest
        CodeActionResolveOp
        CodeLensResolveOp
        CompletionItemResolveOp
        DocumentLinkResolveOp
        Exit
        Initialized
        InitializeOp
        InlayHintResolveOp
        NotebookDocumentDidChange
        NotebookDocumentDidClose
        NotebookDocumentDidOpen
        NotebookDocumentDidSave
        Progress
        SetTrace
        ShutdownOp
        TextDocumentCodeActionOp
        TextDocumentCodeLensOp
        TextDocumentColorPresentationOp
        TextDocumentCompletionOp
        TextDocumentDeclarationOp
        TextDocumentDefinitionOp
        TextDocumentDiagnosticOp
        TextDocumentDidChange
        TextDocumentDidClose
        TextDocumentDidOpen
        TextDocumentDidSave
        TextDocumentDocumentColorOp
        TextDocumentDocumentHighlightOp
        TextDocumentDocumentLinkOp
        TextDocumentDocumentSymbolOp
        TextDocumentFoldingRangeOp
        TextDocumentFormattingOp
        TextDocumentHoverOp
        TextDocumentImplementationOp
        TextDocumentInlayHintOp
        TextDocumentInlineValueOp
        TextDocumentLinkedEditingRangeOp
        TextDocumentMonikerOp
        TextDocumentOnTypeFormattingOp
        TextDocumentPrepareCallHierarchyOp
        TextDocumentPrepareRenameOp
        TextDocumentPrepareTypeHierarchyOp
        TextDocumentRangeFormattingOp
        TextDocumentReferencesOp
        TextDocumentRenameOp
        TextDocumentSelectionRangeOp
        TextDocumentSemanticTokensFullDeltaOp
        TextDocumentSemanticTokensFullOp
        TextDocumentSemanticTokensRangeOp
        TextDocumentSignatureHelpOp
        TextDocumentTypeDefinitionOp
        TextDocumentWillSave
        TextDocumentWillSaveWaitUntilOp
        TypeHierarchySubtypesOp
        TypeHierarchySupertypesOp
        WindowWorkDoneProgressCancel
        WorkspaceDiagnosticOp
        WorkspaceDidChangeConfiguration
        WorkspaceDidChangeWatchedFiles
        WorkspaceDidChangeWorkspaceFolders
        WorkspaceDidCreateFiles
        WorkspaceDidDeleteFiles
        WorkspaceDidRenameFiles
        WorkspaceExecuteCommandOp
        WorkspaceSymbolOp
        WorkspaceSymbolResolveOp
        WorkspaceWillCreateFilesOp
        WorkspaceWillDeleteFilesOp
        WorkspaceWillRenameFilesOp
    ]
}
