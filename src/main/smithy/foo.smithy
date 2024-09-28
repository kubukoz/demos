$version: "2"

namespace foo

apply com.amazonaws.sagemaker#ListEndpoints @smithy4s.meta#only

apply com.amazonaws.sagemaker#ListApps @smithy4s.meta#only

apply com.amazonaws.sagemaker#ListDomains @smithy4s.meta#only
