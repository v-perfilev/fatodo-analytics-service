package contracts.activitycontroller

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name 'write user activity'
    description 'should return status 200'
    request {
        method PUT()
        url("/api/activity")
        headers {
            contentType applicationJson()
            header 'Authorization': $(
                    consumer(containing("Bearer")),
                    producer("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI4ZjlhN2NhZS03M2M4LTRhZDYtYjEzNS01YmQxMDliNTFkMmUiLCJ1c2VybmFtZSI6InRlc3RfdXNlciIsImF1dGhvcml0aWVzIjoiUk9MRV9VU0VSIiwiaWF0IjowLCJleHAiOjMyNTAzNjc2NDAwfQ.Go0MIqfjREMHOLeqoX2Ej3DbeSG7ZxlL4UAvcxqNeO-RgrKUCrgEu77Ty1vgR_upxVGDAWZS-JfuSYPHSRtv-w")
            )
        }
        body([
                "deviceType": $(
                        consumer(anyNonEmptyString()),
                        producer("WEB")
                ),
                "deviceId"  : $(
                        consumer(anyNonEmptyString()),
                        producer("test_device_id")
                ),
        ])
    }
    response {
        status 200
    }
}
